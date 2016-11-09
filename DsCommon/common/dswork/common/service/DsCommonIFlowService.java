package dswork.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dswork.common.dao.DsCommonUtilIFlow;
import dswork.common.model.IFlowPiData;
import dswork.common.model.IFlowTask;
import dswork.common.model.IFlowWaiting;

@Service
public class DsCommonIFlowService
{
	@Autowired
	private DsCommonUtilIFlow util;

	public IFlowWaiting saveFlowStart(String alias, String ywlsh, String sblsh, String account, String name, int piDay, boolean isWorkDay, String taskInterface)
	{
		return util.saveFlowStart(alias, ywlsh, sblsh, account, name, piDay, isWorkDay, taskInterface);
	}

	public String saveStart(String alias, String ywlsh, String sblsh, String account, String name, int piDay, boolean isWorkDay, String taskInterface)
	{
		return util.saveStart(alias, ywlsh, sblsh, account, name, piDay, isWorkDay, taskInterface);
	}
	
	public void saveStop(Long piid)
	{
		util.saveStop(piid);
	}

	public boolean saveProcess(Long waitid, String[] nextTalias, String account, String name, String resultType, String resultMsg)
	{
		return util.saveProcess(waitid, nextTalias, account, name, resultType, resultMsg);
	}
	
	public List<IFlowWaiting> queryFlowWaiting(String account)
	{
		return util.queryFlowWaiting(account);
	}

	public IFlowWaiting getFlowWaiting(Long waitid)
	{
		return util.getFlowWaiting(waitid);
	}
	
	public void updateFlowWaitingUser(Long waitid, String tuser)
	{
		util.updateFlowWaitingUser(waitid, tuser);
	}
	
	public List<IFlowTask> queryFlowTask(Long flowid)
	{
		return util.queryFlowTask(flowid);
	}

	public List<IFlowPiData> queryFlowPiData(Long piid)
	{
		return util.queryFlowPiData(piid);
	}
}
