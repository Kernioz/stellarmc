package net.krakeens.redis.jedis;

import java.util.LinkedList;
import java.util.Queue;

public class Queable {
  private Queue<Response<?>> pipelinedResponses = new LinkedList<Response<?>>();

  protected void clean() {
    pipelinedResponses.clear();
  }

  protected Response<?> generateResponse(Object data) {
    Response<?> response = pipelinedResponses.poll();
    if (response != null) {
      response.set(data);
    }
    return response;
  }

  protected <T> Response<T> getResponse(Builder<T> builder) {
    Response<T> lr = new Response<T>(builder);
    pipelinedResponses.add(lr);
    return lr;
  }

  protected boolean hasPipelinedResponse() {
    return pipelinedResponses.size() > 0;
  }

  protected int getPipelinedResponseLength() {
    return pipelinedResponses.size();
  }
}
