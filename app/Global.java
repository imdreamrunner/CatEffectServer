import com.avaje.ebean.Ebean;
import models.Manager;
import play.Application;
import play.GlobalSettings;
import play.libs.Yaml;

import java.util.List;
import java.util.Map;

public class Global extends GlobalSettings {

    public void onStart(Application app) {
        InitialData.insert(app);
    }

    static class InitialData {

        public static void insert(Application app) {
            if(Ebean.find(Manager.class).findRowCount() == 0) {
                Map<String,List<Object>> all = (Map<String,List<Object>>)Yaml.load("initial_data.yml");

                Ebean.save(all.get("canteens"));
                Ebean.save(all.get("stalls"));
                Ebean.save(all.get("managers"));
            }
        }

    }

}