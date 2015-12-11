
package appcpanama.logicstudio.net.appcpanama.services;

public interface ServiceCallBack<T> {
    void onPostExecute(T result);
    void onException(Exception ex);
}
