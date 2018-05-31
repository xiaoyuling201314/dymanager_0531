package com.dayuan.controller;

import java.awt.Color;
import java.awt.GradientPaint;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GeneratorImage extends BaseController {
//	public ActionForward writeImage4(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
//
//		MessageResources messageResources = this.getResources(request);
//		GradientPaint[] gradientPaintArray = new GradientPaint[] { new GradientPaint(0.0f, 0.0f, Color.blue, 0.0f, 0.0f, new Color(0, 0, 64)),
//				new GradientPaint(0.0f, 0.0f, Color.green, 0.0f, 0.0f, new Color(0, 64, 0)),
//				new GradientPaint(0.0f, 0.0f, Color.red, 0.0f, 0.0f, new Color(64, 0, 0)) };
//		String path1 = TrafficAction.class.getResource("/").getPath();
//		String path2 = "/images/temp/trafficstatistics3.jpg";
//		String path = path1.substring(0, path1.indexOf("/WEB-INF")) + path2;
//		List<Traffic> list = (List<Traffic>) request.getSession().getAttribute("realtimeList");
//		try {
//			JfreeChartUtil.print(path, 1500, 400, getCategoryDataset4(list, request), gradientPaintArray, Tools.getMessage(messageResources, request,
//					"traffic.realtimetraffic.pic.title4"), Tools.getMessage(messageResources, request, "traffic.realtimetraffic.pic.xlabel"), Tools.getMessage(
//					messageResources, request, "traffic.realtimetraffic.pic.ylabel1"));
//			File file = new File(path);
//			FileInputStream inputStream = new FileInputStream(path);
//			byte[] b = new byte[(int) file.length()];
//			inputStream.read(b);
//			inputStream.close();
//			response.getOutputStream().write(b);
//		} catch (IOException e) {
//			if (!e.getClass().getSimpleName().equals("ClientAbortException")) {
//				e.printStackTrace();
//			}
//		}
//		return null;
//	}
}
