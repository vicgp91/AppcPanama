package appcpanama.logicstudio.net.appcpanama.services;

/**
 * Created by LogicStudio on 30/6/15.
 */
public interface CallBack {
    void onPostExecute(String result);
    void onException(Exception ex);
}