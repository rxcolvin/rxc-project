package com.rxc.rest;

import com.rxc.logging.Logger;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

/**
 * Created by richard.colvin on 21/10/2015.
 */
public class RestEngine {

  private final Tomcat tomcat;
  private final Logger logger;

  public RestEngine(String name, int port, final Logger logger, RestDataModel... restModels) {
    this.logger = logger;
    tomcat = new Tomcat();
    Context ctx = tomcat.addContext("/", new File(".").getAbsolutePath());
    tomcat.setPort(port);

    for (RestDataModel restModel : restModels) {

      final String servletName = restModel.entityName();
      Tomcat.addServlet(ctx, servletName,  new RestDataServlet(restModel));
      ctx.addServletMapping("/" + servletName, servletName);
    }
  }

  public void start() {
    try {
      tomcat.start();
      tomcat.getServer().await();
    } catch (LifecycleException e) {
      throw new RuntimeException(e);
    }
  }

  public void stop() {

  }

  private class RestDataServlet extends HttpServlet {

    private final RestDataModel restModel;

    public RestDataServlet(final RestDataModel restModel) {

      this.restModel = restModel;
    }


    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {

      try {
        ServletOutputStream out = resp.getOutputStream();

        String ret = restModel.get(req.getParameterMap());

        out.write(ret.getBytes());
        out.flush();
        out.close();
      } catch (Exception e) {
          logger.info.write(()-> "Caught Exception", ()->e);
      }
    }
  }


}
