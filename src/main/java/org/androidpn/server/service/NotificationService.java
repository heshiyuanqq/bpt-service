/**
 * 
 */
package org.androidpn.server.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.androidpn.server.model.NotificationPO;

import com.cmri.bpt.entity.auth.AppTokenSession;

/**
 * @author chengqiang.liu
 *
 */
public interface NotificationService {	
	public void saveNotification(NotificationPO notificationPO) throws SQLException;
	public void updateNotification(NotificationPO notificationPO) throws SQLException;
	public NotificationPO queryNotificationById(Long id) throws SQLException;	
	public void createNotifications(List<NotificationPO> notificationPOs) throws SQLException;	
	public NotificationPO queryNotificationByUserName(String userName,String messageId) throws SQLException;	
	public List<NotificationPO> queryNotification(String username) throws SQLException;
	
	public Integer queryNotificationInfoCount(String username) throws SQLException;
	public Integer queryNotificationBusInfoCount(String username) throws SQLException;
	
	public void sendCmd(AppTokenSession tokenSession, String message);
	public void stopCmd(List<AppTokenSession> tokenSessions,String message);
	public void flymodeCmd(List<AppTokenSession>  tokenSessions,String message);
	public void stopCaseLogCmd(List<AppTokenSession> tokenSessions,String message);
	public void receiveLogCmd(AppTokenSession tokenSession,String message);
	public void ueupdateCmd(AppTokenSession tokenSession,String messageTitle,String message);
	public void syncTimeCmd(AppTokenSession tokenSession,String message);
	public void storeCallCmd(AppTokenSession tokenSession,String message);
	public boolean expireCallCmd(AppTokenSession tokenSession,String message);
	public void uecontrolCmd(AppTokenSession tokenSession,String messageTitle,String message);
	
	public List<AppTokenSession> getLiveSession(Integer  userId);
	
	public Map<String, Boolean>   getAliveMap();
	
}
