package com.wileyedge.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Area {
	
	private BigDecimal size;

	public BigDecimal getSize() {
		return size;
	}

	public void setSize(BigDecimal size) {
		this.size = size;
	}
	
	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || getClass() != o.getClass()) return false;
	    Area area = (Area) o;
	    return size != null && size.compareTo(area.size) == 0;
	}

	@Override
	public int hashCode() {
	    return Objects.hash(size);
	}

}
