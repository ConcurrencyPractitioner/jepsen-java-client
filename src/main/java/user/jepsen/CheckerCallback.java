package user.jepsen;

public interface CheckerCallback {
    public void check(Object checker, Object test, Object history, Object opts);
}
