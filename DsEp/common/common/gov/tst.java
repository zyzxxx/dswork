package common.gov;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;

import MQAPI.ApplicationOB;

public class tst
{
	public static void main(String[] args)
	{
		ApplicationOB entity = new ApplicationOB();
		entity.setSBLSH("sdfsdfsdf");
		entity.setSXBM("'''''''''极 是人伸腿是枯进 晨地 无可奈何花落去 顶替顶替顶替顶替枯无可奈何无可奈何地顶替");
		entity.setSXMC("极 是人伸腿是枯进 晨地 无可奈何花落去 顶替顶替顶替顶替枯无可奈何无可奈何地顶替");
		entity.setFSXBM("极 是人伸腿是枯进 晨地 无可奈何花落去 顶替顶替顶替顶替枯无可奈何无可奈何地顶替");
		entity.setFSXMC("极 是人伸腿是枯进 晨地 无可奈何花落去 顶替顶替顶替顶替枯无可奈何无可奈何地顶替");
		entity.setSQRLX("极 是人伸腿是枯进 晨地 无可奈何花落去 顶替顶替顶替顶替枯无可奈何无可奈何地顶替");
		entity.setSQRMC("极 是人伸腿是枯进 晨地 无可奈何花落去 顶替顶替顶替顶替枯无可奈何无可奈何地顶替");
		entity.setSQRZJLX("极 是人伸腿是枯进 晨地 无可奈何花落去 顶替顶替顶替顶替枯无可奈何无可奈何地顶替");
		entity.setSQRZJHM("极 是人伸腿是枯进 晨地 无可奈何花落去 顶替顶替顶替顶替枯无可奈何无可奈何地顶替");
		entity.setLXRXM("极 是人伸腿是枯进 晨地 无可奈何花落去 顶替顶替顶替顶替枯无可奈何无可奈何地顶替");
		entity.setLXRZJLX("极 是人伸腿是枯进 晨地 无可奈何花落去 顶替顶替顶替顶替枯无可奈何无可奈何地顶替");
		entity.setLXRSFZJHM("极 是人伸腿是枯进 晨地 无可奈何花落去 顶替顶替顶替顶替枯无可奈何无可奈何地顶替");
		entity.setLXRSJ("极 是人伸腿是枯进 晨地 无可奈何花落去 顶替顶替顶替顶替枯无可奈何无可奈何地顶替");
		entity.setLXRYX("极 是人伸腿是枯进 晨地 无可奈何花落去 顶替顶替顶替顶替枯无可奈何无可奈何地顶替");
		entity.setSBXMMC("极 是人伸腿是枯进 晨地 无可奈何花落去 顶替顶替顶替顶替枯无可奈何无可奈何地顶替");
		entity.setSBCLQD("极 是人伸腿是枯进 晨地 无可奈何花落去 顶替顶替顶替顶替枯无可奈何无可奈何地顶替");
		entity.setTJFS("极 是人伸腿是枯进 晨地 无可奈何花落去 顶替顶替顶替顶替枯无可奈何无可奈何地顶替");
		entity.setSBHZH("极 是人伸腿是枯进 晨地 无可奈何花落去 顶替顶替顶替顶替枯无可奈何无可奈何地顶替");
		entity.setSBSJ(new Date());
		entity.setSBJTWD("极 是人伸腿是枯进 晨地 无可奈何花落去 顶替顶替顶替顶替枯无可奈何无可奈何地顶替");
		entity.setXZQHDM("极 是人伸腿是枯进 晨地 无可奈何花落去 顶替顶替顶替顶替枯无可奈何无可奈何地顶替");
		entity.setYSBLSH("极 是人伸腿是枯进 晨地 无可奈何花落去 顶替顶替顶替顶替枯无可奈何无可奈何地顶替");
		entity.setSJBBH(1);
		entity.setSQRDH("极 是人伸腿是枯进 晨地 无可奈何花落去 顶替顶替顶替顶替枯无可奈何无可奈何地顶替");
		entity.setXMHGCBH("极 是人伸腿是枯进 晨地 无可奈何花落去 顶替顶替顶替顶替枯无可奈何无可奈何地顶替");
		entity.setLXWH("极 是人伸腿是枯进 晨地 无可奈何花落去 顶替顶替顶替顶替枯无可奈何无可奈何地顶替");
		entity.setBZ("极 是人伸腿是枯进 晨地 无可奈何花落去 顶替顶替顶替顶替枯无可奈何无可奈何地顶替");
		entity.setBYZDA("极 是人伸腿是枯进 晨地 无可奈何花落去 顶替顶替顶替顶替枯无可奈何无可奈何地顶替");
		entity.setBYZDB("极 是人伸腿是枯进 晨地 无可奈何花落去 顶替顶替顶替顶替枯无可奈何无可奈何地顶替");
		entity.setBYZDC("极 是人伸腿是枯进 晨地 无可奈何花落去 顶替顶替顶替顶替枯无可奈何无可奈何地顶替");
		entity.setBYZDD(new Date());
		try
		{
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
			objectOutputStream.writeObject(entity);
			String v = byteArrayOutputStream.toString("ISO-8859-1");
			objectOutputStream.close();
			byteArrayOutputStream.close();
			System.out.println("********************");
			System.out.println("********************");
			System.out.println("v.length = " + v.getBytes("ISO-8859-1").length);
			ByteArrayInputStream bais = new ByteArrayInputStream(v.getBytes("ISO-8859-1"));
			java.io.ObjectInputStream ois = new ObjectInputStream(bais);
			ApplicationOB o = (ApplicationOB)ois.readObject();
			ois.close();
			bais.close();

			System.out.println("o.getSJBBH()" + o.getBYZDA());
			System.out.println("********************");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
