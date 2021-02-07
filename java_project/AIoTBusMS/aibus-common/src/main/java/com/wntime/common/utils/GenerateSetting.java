package com.wntime.common.utils;

import cn.hutool.setting.Setting;

import java.util.List;

public class GenerateSetting {
	public static final void agent(
			String clusterId,
			String clusterName,
			String clusterCode,
			String hostId,
			String hostName,
			String bindIp,
			List<GFSInfo> infos,
			String serverUrl,
			String authUrl,
			String authName,
			String password,
			String absolutePath
	) {

		Setting setting = new Setting();
		setting.set("cluster", "id", clusterId);
		setting.set("cluster", "name", clusterName);
		setting.set("cluster", "code", clusterCode);

		setting.set("host", "host_id", hostId);
		setting.set("host", "host_name", hostName);
		setting.set("host", "bind_ip", bindIp);
		for (GFSInfo info : infos) {
			setting.set("node", info.getNodeId(), info.getFilePath());
		}


		setting.set("store", "store_server_url", serverUrl);

		setting.set("auth", "auth_url", authUrl);
		setting.set("auth", "auth_name", authName);
		setting.set("auth", "password", password);

		//setting.toString();
		setting.autoLoad(false);
		setting.store(absolutePath);

	}

	public static class GFSInfo {
		String nodeId;
		String filePath;

		public GFSInfo(String nodeId, String filePath) {
			this.nodeId = nodeId;
			this.filePath = filePath;
		}

		public String getNodeId() {
			return nodeId;
		}

		public void setNodeId(String nodeId) {
			this.nodeId = nodeId;
		}

		public String getFilePath() {
			return filePath;
		}

		public void setFilePath(String filePath) {
			this.filePath = filePath;
		}
	}
}
