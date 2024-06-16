import java.security.MessageDigest;
import java.util.Base64;
import java.util.Scanner;

class ChainOfResponsibilityDemo {

    /**
     * Accepts a request and returns new request with data wrapped in the tag
     * <transaction>...</transaction>
     */
    static RequestHandler wrapInTransactionTag = req -> new Request(String.format(
            "<transaction>%s</transaction>",
            req.getData()));

    /**
     * Accepts a request and returns a new request with calculated digest inside the tag
     * <digest>...</digest>
     */
    static RequestHandler createDigest = req -> {
        String digest = "";
        try {
            final MessageDigest md5 = MessageDigest.getInstance("MD5");
            final byte[] digestBytes = md5.digest(req.getData().getBytes("UTF-8"));
            digest = new String(Base64.getEncoder().encode(digestBytes));
        } catch (Exception ignored) {
            System.out.println("An error occurred");
        }
        return new Request(req.getData() + String.format("<digest>%s</digest>", digest));
    };

    /**
     * Accepts a request and returns a new request with data wrapped in the tag
     * <request>...</request>
     */
    static RequestHandler wrapInRequestTag = req -> new Request(String.format(
            "<request>%s</request>",
            req.getData()));

    /**
     * It represents a handler and has two methods: one for handling requests and other for
     * combining handlers
     */
    @FunctionalInterface
    interface RequestHandler {

        Request handle(Request request);

        default RequestHandler andThen(RequestHandler after) {
            return req -> after.handle(this.handle(req));
        }

    }

    static RequestHandler commonRequestHandler = wrapInTransactionTag.andThen(createDigest)
                                                                     .andThen(wrapInRequestTag);

    /**
     * Immutable class for representing requests. If you need to change the request data then create
     * new request.
     */
    static class Request {

        private final String data;

        public Request(String requestData) {
            this.data = requestData;
        }

        public String getData() {
            return data;
        }

    }

    // Don't change the code below
    public static void main(String[] args)
            throws Exception
    {

        final Scanner scanner = new Scanner(System.in);

        final String requestData = scanner.nextLine();

        final Request notCompletedRequest = new Request(requestData);

        System.out.println(commonRequestHandler.handle(notCompletedRequest).getData());
    }

}
