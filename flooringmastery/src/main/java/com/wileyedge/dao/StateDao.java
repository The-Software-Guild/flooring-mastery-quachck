package com.wileyedge.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.wileyedge.model.State;

@Repository
public interface StateDao {
	public List<State> getAllStates();
}
