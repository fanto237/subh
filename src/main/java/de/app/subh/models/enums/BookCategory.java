package de.app.subh.models.enums;

import lombok.Getter;

@Getter
public enum BookCategory {
	MANGAS("Mangas"),
	SCIENCE("Wissenschaft"),
	MATHS("Mathematik"), 
	PROGRAMMING("Programmierung"),
	PHYSIC("Physik"),
	GEO("Geografie"),
	PHYLO("Philosophie");
	
	private String displayText;
	
	private BookCategory(String displayText) {
		this.displayText = displayText;
	}
	
	@Override
	public String toString() {
		return displayText;
	}
}
