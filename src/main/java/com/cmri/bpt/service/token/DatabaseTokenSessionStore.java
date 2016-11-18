package com.cmri.bpt.service.token;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.androidpn.server.service.UserNotFoundException;//又是直接使用了androidpn的api导致耦合，所以此类要重新加架构
import org.androidpn.server.xmpp.session.ClientSession;
import org.androidpn.server.xmpp.session.SessionManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmri.bpt.common.token.AbstractTokenSessionStore;
import com.cmri.bpt.common.token.TokenSession;
import com.cmri.bpt.common.token.TokenSessionStore;
import com.cmri.bpt.common.util.DeviceIdGenerator;
import com.cmri.bpt.common.util.JsonUtil;
import com.cmri.bpt.entity.auth.AppTokenSession;
import com.cmri.bpt.service.token.mapper.AppTokenSessionMapper;

@Service
public class DatabaseTokenSessionStore extends AbstractTokenSessionStore implements TokenSessionStore {

	static Logger logger = Logger.getLogger(DatabaseTokenSessionStore.class);
	static Logger heartLogger = Logger.getLogger("user." + DatabaseTokenSessionStore.class);
	@Autowired
	private AppTokenSessionMapper appTokenSessionMapper;

	@Override
	public void InitSessions() {

		List<AppTokenSession> sessions = appTokenSessionMapper.queryAll();

		Map<Integer, Integer> user_dev_map = new HashMap<Integer, Integer>();

		for (AppTokenSession appSession : sessions) {
			tokenSessionMap.put(appSession.getToken(), appSession);

			Integer val = user_dev_map.get(appSession.getUserId());

			if (val == null) {
				user_dev_map.put(appSession.getUserId(), appSession.getSysId());
			} else if (val < appSession.getSysId()) {
				user_dev_map.put(appSession.getUserId(), appSession.getSysId());
			}

		}

		DeviceIdGenerator.init(user_dev_map);
		new TokenSessionOnlineExecutor().start();

	}

	private AppTokenSession find(String token) {

		return appTokenSessionMapper.selectByToken(token);
	}

	// TokenSession过期检查周期
	private static final int ExpirationCheckInterval = 60 * 1000;

	// 过期执行器（清除过期的TokenSession）
	class TokenSessionExpirationExecutor extends Thread {

		@Override
		public void run() {
			while (true) {

				if (tokenSessionExpireStrategy != null) {
					for (Map.Entry<String, TokenSession> tokenSessionEntry : tokenSessionMap.entrySet()) {
						String token = tokenSessionEntry.getKey();
						TokenSession tokenSession = tokenSessionEntry.getValue();
						if (tokenSessionExpireStrategy.isTokenSessionExpired(tokenSession)) {
							//
							notifyEventListeners(TokenSessionEvent.Expired, tokenSession);
							//
							removeTokenSessionInner(token);
						}
					}
				}
				//
				try {
					Thread.sleep(ExpirationCheckInterval);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static final int OnlineCheckInterval = 60 * 1000;

	class TokenSessionOnlineExecutor extends Thread {

		@Override
		public void run() {

			while (true) {

				try {
					Thread.sleep(OnlineCheckInterval);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				Collection<ClientSession> clientS = SessionManager.getInstance().getSessions();
				Map<String, Boolean> aliveMap = new HashMap<String, Boolean>();

				for (ClientSession s : clientS) {

					try {

						aliveMap.put(s.getUsername(), s.getPresence().isAvailable());

					} catch (UserNotFoundException e) {

						e.printStackTrace();
					}
				}

				List<Integer> onlineIds = new ArrayList<Integer>();
				List<Integer> offlineIds = new ArrayList<Integer>();

				for (Map.Entry<String, TokenSession> tokenSessionEntry : tokenSessionMap.entrySet()) {

					TokenSession tokenSession = tokenSessionEntry.getValue();

					if (aliveMap.get(tokenSession.getXppId()) != null && aliveMap.get(tokenSession.getXppId())) {
						onlineIds.add(tokenSession.getId());
						tokenSession.setOnline(true);
					} else {
						offlineIds.add(tokenSession.getId());
						tokenSession.setOnline(false);
					}
				}
				
				heartLogger.debug("检测心跳："+ JsonUtil.toJson(aliveMap));				

				if (!onlineIds.isEmpty())
					appTokenSessionMapper.updateOnline(true, onlineIds.toArray(new Integer[0]));
				if (!offlineIds.isEmpty())
					appTokenSessionMapper.updateOnline(false, offlineIds.toArray(new Integer[0]));

				//

			}
		}
	}

	@Override
	public boolean touchTokenSession(String token) {

		syncTokenSession(token);
		boolean rs = super.touchTokenSession(token);

		if (rs) {

			// 更新 token 数据库
			TokenSession tokenSession = this.tokenSessionMap.get(token);
			AppTokenSession appSession = (AppTokenSession) tokenSession;

			appTokenSessionMapper.updateSession(appSession);

		}
		return rs;
	}

	@Override
	public TokenSession removeTokenSession(String token) {
		TokenSession tokenSession = getTokenSession(token);
		if (tokenSession != null) {
			this.removeTokenSessionInner(token);
		}
		return tokenSession;
	}

	// 删除指定的TokenSession
	private void removeTokenSessionInner(String token) {

		TokenSession tokenSession = tokenSessionMap.remove(token);
		if (tokenSession == null) {
			return;
		}

		AppTokenSession appTokenSession = this.find(token);

		if (appTokenSession == null) {
			return;
		}

		appTokenSessionMapper.deleteSession(appTokenSession);
		notifyEventListeners(TokenSessionEvent.Removed, tokenSession);
	}

	@Override
	public TokenSession getTokenSession(Integer userId, Integer appId, String xppId) {

		Collection<TokenSession> sessions = tokenSessionMap.values();

		for (TokenSession session : sessions) {

			if (session.getUserId() != null && session.getUserId().equals(userId) && session.getAppId() != null && session.getAppId().equals(appId) && session.getXppId() != null && session.getXppId().equals(xppId)

			) {

				return session;
			}
		}

		return null;
	}

	@Override
	public TokenSession createTokenSession(Integer userId, Integer appId, String xppId) {

		TokenSession tokenSession = this.getTokenSession(userId, appId, xppId);

		if (tokenSession == null) {

			String token = this.tokenGenerator.generateToken();
			//
			tokenSession = new AppTokenSession();
			tokenSession.setToken(token);
			tokenSession.setUserId(userId);
			tokenSession.setSysId(DeviceIdGenerator.getId(userId));
			tokenSession.setAppId(appId);
			tokenSession.setXppId(xppId);
			tokenSession.setLastAccessTime(new Date());
			//
			this.tokenSessionMap.put(token, tokenSession);

			AppTokenSession appSession = (AppTokenSession) tokenSession;

			appTokenSessionMapper.insertSession(appSession);

			this.notifyEventListeners(TokenSessionEvent.Created, tokenSession);

		} else {
			this.touchTokenSession(tokenSession.getToken());
		}
		return tokenSession;
	}

	@Override
	public TokenSession updateTokenSession(TokenSession session) {

		AppTokenSession appSession = (AppTokenSession) session;
		appTokenSessionMapper.updateSession(appSession);

		return session;

	}

	@Override
	public boolean syncTokenSession(String token) {

		AppTokenSession tokenSession = appTokenSessionMapper.selectByToken(token);
		tokenSessionMap.put(token, tokenSession);

		return true;
	}

}
