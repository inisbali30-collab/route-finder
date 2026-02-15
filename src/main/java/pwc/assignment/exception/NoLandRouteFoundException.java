package pwc.assignment.exception;

public class NoLandRouteFoundException extends RuntimeException {

  public NoLandRouteFoundException(final String origin, final String destination) {
    super("no land route found from " + origin + " to " + destination);
  }
}