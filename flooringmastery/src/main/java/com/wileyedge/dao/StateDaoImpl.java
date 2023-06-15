package com.wileyedge.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.stereotype.Repository;

import com.wileyedge.model.State;

@Repository
public class StateDaoImpl implements StateDao {

	private static final String STATE_FILE = "src/main/resources/production-data/Data/Taxes.txt";

	public List<State> getAllStates() {
		List<State> states = new ArrayList<>();
		File file = new File(STATE_FILE);

		try (Scanner scanner = new Scanner(file)) {
			scanner.nextLine(); // Skip the header line
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] parts = line.split(",");
				State state = new State();
				state.setAbbrv(parts[0]);
				state.setName(parts[1]);
				state.setTaxRate(new BigDecimal(parts[2]));
				states.add(state);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return states;
	}
}

