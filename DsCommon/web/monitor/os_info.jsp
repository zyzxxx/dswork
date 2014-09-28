<%@page language="java" pageEncoding="UTF-8"
import="com.sun.management.OperatingSystemMXBean,java.lang.management.ManagementFactory"
%><%
		OperatingSystemMXBean osmb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
		Runtime r = Runtime.getRuntime();
		StringBuilder sb = new StringBuilder();
		sb.append("{")
		.append("osname").append(":\"").append(osmb.getName()).append("\",")
		.append("osarch").append(":\"").append(osmb.getArch()).append("\",")
		.append("osversion").append(":\"").append(osmb.getVersion()).append("\",")
		.append("oscpu").append(":\"").append(osmb.getAvailableProcessors()).append("\",")
		.append("osmemory").append(":\"").append(osmb.getTotalSwapSpaceSize() / 1024 / 1024).append("\",")
		.append("osphysicalmemory").append(":\"").append(osmb.getTotalPhysicalMemorySize() / 1024 / 1024).append("\",")
		.append("free").append(":\"").append(osmb.getFreePhysicalMemorySize() / 1024 / 1024).append("\",")
		.append("swap").append(":\"").append(osmb.getFreeSwapSpaceSize() / 1024 / 1024).append("\",")
		.append("runmax").append(":\"").append(r.maxMemory() / 1024 / 1024).append("\",")
		.append("runtotal").append(":\"").append(r.totalMemory() / 1024 / 1024).append("\",")
		.append("runfree").append(":\"").append(r.freeMemory() / 1024 / 1024).append("\",")
		.append("runcpu").append(":\"").append(r.availableProcessors())
		.append("\"}");
%><%=sb.toString()%>