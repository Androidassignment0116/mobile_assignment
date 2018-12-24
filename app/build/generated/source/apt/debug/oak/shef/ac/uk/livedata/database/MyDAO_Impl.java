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
import java.lang.Override;
import java.lang.String;
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
        return "INSERT OR ABORT INTO `picinfo_database`(`id`,`number`,`title`,`description`,`image`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, PicinfoData value) {
        stmt.bindLong(1, value.getId());
        stmt.bindLong(2, value.getNumber());
        if (value.getTitle() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getTitle());
        }
        if (value.getDescription() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getDescription());
        }
        if (value.getImage() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindBlob(5, value.getImage());
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
  public LiveData<PicinfoData> retrieveOnePicinfo() {
    final String _sql = "SELECT * FROM picinfo_database ORDER BY RANDOM() LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return new ComputableLiveData<PicinfoData>() {
      private Observer _observer;

      @Override
      protected PicinfoData compute() {
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
          final int _cursorIndexOfNumber = _cursor.getColumnIndexOrThrow("number");
          final int _cursorIndexOfTitle = _cursor.getColumnIndexOrThrow("title");
          final int _cursorIndexOfDescription = _cursor.getColumnIndexOrThrow("description");
          final int _cursorIndexOfImage = _cursor.getColumnIndexOrThrow("image");
          final PicinfoData _result;
          if(_cursor.moveToFirst()) {
            final int _tmpNumber;
            _tmpNumber = _cursor.getInt(_cursorIndexOfNumber);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final byte[] _tmpImage;
            _tmpImage = _cursor.getBlob(_cursorIndexOfImage);
            _result = new PicinfoData(_tmpNumber,_tmpTitle,_tmpDescription,_tmpImage);
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            _result.setId(_tmpId);
          } else {
            _result = null;
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
}
