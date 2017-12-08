package com.ycuwq.todo.data.source.local.dao;

import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;

import com.ycuwq.todo.data.source.local.AppDb;

import org.junit.After;
import org.junit.Before;

/**
 * 测试Room的数据库的基类
 * Created by yangchen on 2017/12/8.
 */
public abstract class DbTest {
	protected AppDb db;

	@Before
	public void initDb() {
		db = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(), AppDb.class).build();
	}

	@After
	public void closeDb() {
		db.close();
	}
}
