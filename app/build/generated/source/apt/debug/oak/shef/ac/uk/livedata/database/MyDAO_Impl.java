package oak.shef.ac.uk.livedata.database;

import android.arch.lifecycle.ComputableLiveData;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.db.SupportSQLiteStatement;
import android.arch.persistence.room.EntityDeletionOrUpdateAdapter;
import android.arch.persistence.room.EntityInsertionAdapter;
import android.arch.persistence.room.InvalidationTracker.Observer;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomSQLiteQuery;
import android.database.Cursor;
import android.support.annotation.NonNull;
import java.lang.Float;
import java.lang.Override;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MyDAO_Impl implements MyDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfPicinfoData;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfPicinfoData;

  public MyDAO_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfPicinfoData = new EntityInsertionAdapter<PicinfoData>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `picinfo_database`(`id`,`title`,`description`,`datetime`,`latitude`,`longitude`,`image`) VALUES (nullif(?, 0),?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, PicinfoData value) {
        stmt.bindLong(1, value.getId());
        if (value.getTitle() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getTitle());
        }
        if (value.getDescription() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getDescription());
        }
        if (value.getDatetime() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getDatetime());
        }
        if (value.getLatitude() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindDouble(5, value.getLatitude());
        }
        if (value.getLongitude() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindDouble(6, value.getLongitude());
        }
        if (value.getImage() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindBlob(7, value.getImage());
        }
      }
    };
    this.__deletionAdapterOfPicinfoData = new EntityDeletionOrUpdateAdapter<PicinfoData>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `picinfo_database` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, PicinfoData value) {
        stmt.bindLong(1, value.getId());
      }
    };
  }

  @Override
  public void insertAll(PicinfoData... picinfoData) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfPicinfoData.insert(picinfoData);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insert(PicinfoData picinfoData) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfPicinfoData.insert(picinfoData);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(PicinfoData picinfoData) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfPicinfoData.handle(picinfoData);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteAll(PicinfoData... picinfoData) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfPicinfoData.handleMultiple(picinfoData);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public LiveData<List<byte[]>> getallimage() {
    final String _sql = "SELECT image FROM picinfo_database";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return new ComputableLiveData<List<byte[]>>() {
      private Observer _observer;

      @Override
      protected List<byte[]> compute() {
        if (_observer == null) {
          _observer = new Observer("picinfo_database") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final List<byte[]> _result = new ArrayList<byte[]>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final byte[] _item;
            _item = _cursor.getBlob(0);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    }.getLiveData();
  }

  @Override
  public LiveData<List<PicinfoData>> getall() {
    final String _sql = "SELECT * FROM picinfo_database";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return new ComputableLiveData<List<PicinfoData>>() {
      private Observer _observer;

      @Override
      protected List<PicinfoData> compute() {
        if (_observer == null) {
          _observer = new Observer("picinfo_database") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
          final int _cursorIndexOfTitle = _cursor.getColumnIndexOrThrow("title");
          final int _cursorIndexOfDescription = _cursor.getColumnIndexOrThrow("description");
          final int _cursorIndexOfDatetime = _cursor.getColumnIndexOrThrow("datetime");
          final int _cursorIndexOfLatitude = _cursor.getColumnIndexOrThrow("latitude");
          final int _cursorIndexOfLongitude = _cursor.getColumnIndexOrThrow("longitude");
          final int _cursorIndexOfImage = _cursor.getColumnIndexOrThrow("image");
          final List<PicinfoData> _result = new ArrayList<PicinfoData>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final PicinfoData _item;
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final String _tmpDatetime;
            _tmpDatetime = _cursor.getString(_cursorIndexOfDatetime);
            final Float _tmpLatitude;
            if (_cursor.isNull(_cursorIndexOfLatitude)) {
              _tmpLatitude = null;
            } else {
              _tmpLatitude = _cursor.getFloat(_cursorIndexOfLatitude);
            }
            final Float _tmpLongitude;
            if (_cursor.isNull(_cursorIndexOfLongitude)) {
              _tmpLongitude = null;
            } else {
              _tmpLongitude = _cursor.getFloat(_cursorIndexOfLongitude);
            }
            final byte[] _tmpImage;
            _tmpImage = _cursor.getBlob(_cursorIndexOfImage);
            _item = new PicinfoData(_tmpTitle,_tmpDescription,_tmpImage,_tmpDatetime,_tmpLatitude,_tmpLongitude);
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            _item.setId(_tmpId);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    }.getLiveData();
  }

  @Override
  public int howManyElements() {
    final String _sql = "SELECT COUNT(*) FROM picinfo_database";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _result;
      if(_cursor.moveToFirst()) {
        _result = _cursor.getInt(0);
      } else {
        _result = 0;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<String> getalldatetime() {
    final String _sql = "SELECT datetime FROM picinfo_database";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final List<String> _result = new ArrayList<String>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final String _item;
        _item = _cursor.getString(0);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public boolean checkexits(String datetime) {
    final String _sql = "SELECT * FROM picinfo_database WHERE datetime = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (datetime == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, datetime);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final boolean _result;
      if(_cursor.moveToFirst()) {
        final int _tmp;
        _tmp = _cursor.getInt(0);
        _result = _tmp != 0;
      } else {
        _result = false;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
