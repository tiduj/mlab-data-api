package org.olabs.portal.api;

import java.util.Collections;
import java.util.Map;
import org.objectlabs.http.HttpMethod;
import org.objectlabs.ns.Uri;
import org.objectlabs.ws.RequestContext;
import org.objectlabs.ws.Resource;
import org.objectlabs.ws.ResourceException;

public class DatabasesResource extends PortalRESTResource {

  private String[] methods = {HttpMethod.GET.name()};

  public String[] getMethods() {
    return (methods);
  }

  @Override
  public Object handleGet(Map parameters, RequestContext context) throws ResourceException {
    final Map<String, String> clusters = getApiConfig().getDatabases();
    return clusters == null ? Collections.emptyList() : clusters.keySet();
  }

  public Resource resolveRelative(Uri uri) {
    if (uri.hasEmptyPath()) {
      return (this);
    }

    String head = uri.getHead();
    if (head == null || head.equals("")) {
      return (this);
    }

    Resource r =
        new MongoDBConnectionResource(getApiConfig().getDatabaseConnection(head))
            .resolve(head);
    r.setParent(this);
    return (r.resolve(uri.getTail()));
  }
}