package pwc.assignment.exception;

public record ExceptionDetails(Long timestamp, Integer status, String path, String error) {

}
