package user.jepsen;

public interface CheckerCallback {
    public void check(Object test, Object history);
}
