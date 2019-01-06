package uk.ac.shef.oak.com6510.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.db.SupportSQLiteOpenHelper.Callback;
import android.arch.persistence.db.SupportSQLiteOpenHelper.Configuration;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.RoomOpenHelper;
import android.arch.persistence.room.RoomOpenHelper.Delegate;
import android.arch.persistence.room.util.TableInfo;
import android.arch.persistence.room.util.TableInfo.Column;
import android.arch.persistence.room.util.TableInfo.ForeignKey;
import android.arch.persistence.room.util.TableInfo.Index;
import java.lang.IllegalStateException;
import java.lang.Override;
import java.lang.String;
import java.util.HashMap;
import java.util.HashSet;

public class MyRoomDatabase_Impl extends MyRoomDatabase {
  private volatile MyDAO _myDAO;

  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(6) {
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `picinfo_database` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `title` TEXT, `description` TEXT, `datetime` TEXT, `latitude` REAL, `longitude` REAL, `path` TEXT)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, \"90d61d1c4138a86f47aefc221078e2e4\")");
      }

      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `picinfo_database`");
      }

      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      protected void validateMigration(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsPicinfoDatabase = new HashMap<String, TableInfo.Column>(7);
        _columnsPicinfoDatabase.put("id", new TableInfo.Column("id", "INTEGER", true, 1));
        _columnsPicinfoDatabase.put("title", new TableInfo.Column("title", "TEXT", false, 0));
        _columnsPicinfoDatabase.put("description", new TableInfo.Column("description", "TEXT", false, 0));
        _columnsPicinfoDatabase.put("datetime", new TableInfo.Column("datetime", "TEXT", false, 0));
        _columnsPicinfoDatabase.put("latitude", new TableInfo.Column("latitude", "REAL", false, 0));
        _columnsPicinfoDatabase.put("longitude", new TableInfo.Column("longitude", "REAL", false, 0));
        _columnsPicinfoDatabase.put("path", new TableInfo.Column("path", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysPicinfoDatabase = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesPicinfoDatabase = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoPicinfoDatabase = new TableInfo("picinfo_database", _columnsPicinfoDatabase, _foreignKeysPicinfoDatabase, _indicesPicinfoDatabase);
        final TableInfo _existingPicinfoDatabase = TableInfo.read(_db, "picinfo_database");
        if (! _infoPicinfoDatabase.equals(_existingPicinfoDatabase)) {
          throw new IllegalStateException("Migration didn't properly handle picinfo_database(uk.ac.shef.oak.com6510.database.PicinfoData).\n"
                  + " Expected:\n" + _infoPicinfoDatabase + "\n"
                  + " Found:\n" + _existingPicinfoDatabase);
        }
      }
    }, "90d61d1c4138a86f47aefc221078e2e4");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    return new InvalidationTracker(this, "picinfo_database");
  }

  @Override
  public MyDAO myDao() {
    if (_myDAO != null) {
      return _myDAO;
    } else {
      synchronized(this) {
        if(_myDAO == null) {
          _myDAO = new MyDAO_Impl(this);
        }
        return _myDAO;
      }
    }
  }
}
