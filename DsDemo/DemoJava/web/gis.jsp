<%@ page language="java" import="java.util.*,
javax.imageio.ImageIO,
java.awt.AlphaComposite,
java.awt.Color,
java.awt.Font,
java.awt.Graphics2D,
java.awt.image.BufferedImage
" pageEncoding="UTF-8"%><%!
static Color colorw = new Color(255, 255, 255);
static Color colorb = new Color(0, 0, 0);
%><%
// 设置页面不缓存
response.setHeader("Pragma", "No-cache");
response.setHeader("Cache-Control", "no-cache");
response.setDateHeader("Expires", 0);
String x = String.valueOf(request.getParameter("x"));
String y = String.valueOf(request.getParameter("y"));
String z = String.valueOf(request.getParameter("z"));
// 在内存中创建图象
int width = 256, height = 256;
BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
// 获取图形上下文
Graphics2D g = (Graphics2D)image.getGraphics();
//Graphics g = image.getGraphics();
// 透明度
//g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
// 设定背景色
g.setColor(colorw);
g.fillRect(0, 0, width, height);
// 设定字体
g.setFont(new Font("Fixedsys", Font.PLAIN, 36));
// 画边框
g.setColor(colorb);
g.drawRect(0, 0, width-1, height-1);
// 将认证码显示到图象中
g.setColor(colorb);// 调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
g.drawString("z:" + z, 30, 60);
g.drawString("y:" + y, 30, 100);
g.drawString("x:" + x, 30, 140);
// 图象生效
g.dispose();
// 输出图象到页面
ImageIO.write(image, "png", response.getOutputStream());
%>