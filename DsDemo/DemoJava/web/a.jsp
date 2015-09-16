<%@ page language="java" pageEncoding="GBK"%>
<%=request.getRemoteAddr()%><br />
<%=getClientIp(request)%><br />
<%=request.getServerName()%>:<%=request.getServerPort()%><br />
<%!
public static String getClientIp(HttpServletRequest request) {
    String ip = request.getHeader("X-Forwarded-For");
    if(ip != null && ip.length() > 0 && !"null".equalsIgnoreCase(ip) && !"unKnown".equalsIgnoreCase(ip)){
        //多次反向代理后会有多个ip值，第一个ip才是真实ip
        int index = ip.indexOf(",");
        if(index != -1){
            return ip.substring(0,index);
        }else{
            return ip;
        }
    }
    ip = request.getHeader("X-Real-IP");
    if(ip != null && ip.length() > 0 && !"null".equalsIgnoreCase(ip) && !"unKnown".equalsIgnoreCase(ip)){
        return ip;
    }
    return request.getRemoteAddr();
}
%>