

package uk.ac.shef.oak.com6510.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

@android.arch.persistence.room.Database(entities = {PicinfoData.class}, version = 6, exportSchema = false)
public abstract class MyRoomDatabase extends RoomDatabase {
    public abstract MyDAO myDao();

    // marking the instance as volatile to ensure atomic access to the variable
    private static volatile MyRoomDatabase INSTANCE;

/**
 * check if there is a database exits, if no database, build one and named picinfo_database.
 * @param context
  * @return uk.ac.shef.oak.com6510.database.MyRoomDatabase
 * @author Gang Chen
 * @creed: assignment
 * @date 2019/1/16 13:46
 */

    public static MyRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MyRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = android.arch.persistence.room.Room.databaseBuilder(context.getApplicationContext(),
                            MyRoomDatabase.class, "picinfo_database")
                            // Wipes and rebuilds instead of migrating if no Migration object.
                            // Migration is not part of this codelab.
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }


    /**
     * Override the onOpen method to populate the database.
     * For this sample, we clear the database every time it is created or opened.
     *
     * If you want to populate the database only when the database is created for the 1st time,
     * override RoomDatabase.Callback()#onCreate
     * @param null
      * @return
     * @author Gang Chen
     * @creed: assignment
     * @date 2019/1/16 13:46
     * @return
     */
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            // do any init operation about any initialisation here
        }
    };

}
