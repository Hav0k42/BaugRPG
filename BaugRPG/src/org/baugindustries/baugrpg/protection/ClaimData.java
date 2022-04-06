package org.baugindustries.baugrpg.protection;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.block.Block;

public class ClaimData {
	
	private UUID playerUUID;
	private Location location1;
	private Location location2;
	private List<Block> tempBlocks;
	private State state;
	private long lastClick;//get this value from World.getGameTIme()
	private String resizingKey;
	
	public ClaimData(UUID uuid, Location loc) {
		playerUUID = uuid;
		tempBlocks = new ArrayList<Block>();
		lastClick = loc.getWorld().getGameTime();
		state = State.IDLE;
		resizingKey = "";
	}
	
	public UUID getUUID() {
		return playerUUID;
	}
	
	public Location getLocation1() {
		return location1;
	}
	
	public Location getLocation2() {
		return location2;
	}
	
	public void setLocation1(Location loc) {
		location1 = loc;
	}
	
	public void setLocation2(Location loc) {
		location2 = loc;
	}
	
	public int[] getNorthEastCorner() {//X+, Z-
		int[] coords = {Math.max(location1.getBlockX(), location2.getBlockX()), Math.min(location1.getBlockZ(), location2.getBlockZ())};
		return coords;
	}
	
	public int[] getSouthEastCorner() {//X+, Z+
		int[] coords = {Math.max(location1.getBlockX(), location2.getBlockX()), Math.max(location1.getBlockZ(), location2.getBlockZ())};
		return coords;
	}
	
	public int[] getSouthWestCorner() {//X-, Z+
		int[] coords = {Math.min(location1.getBlockX(), location2.getBlockX()), Math.max(location1.getBlockZ(), location2.getBlockZ())};
		return coords;
	}
	
	public int[] getNorthWestCorner() {//X-, Z-
		int[] coords = {Math.min(location1.getBlockX(), location2.getBlockX()), Math.min(location1.getBlockZ(), location2.getBlockZ())};
		return coords;
	}
	
	public int[][] getCorners() {
		int[][] corners = {getNorthEastCorner(), getSouthEastCorner(), getSouthWestCorner(), getNorthWestCorner()};
		return corners;
	}
	
	public int[] getLocation1Corner() {
		int[] corner = {location1.getBlockX(), location1.getBlockZ()};
		return corner;
	}
	
	public int[] getLocation2Corner() {
		int[] corner = {location2.getBlockX(), location2.getBlockZ()};
		return corner;
	}
	
	public void addTempBlock(Block block) {
		tempBlocks.add(block);
	}
	
	public void addTempBlocks(List<Block> blocks) {
		tempBlocks.addAll(blocks);
	}
	
	public List<Block> getTempBlocks() {
		return tempBlocks;
	}
	
	public void resetTempBlocks() {
		tempBlocks = new ArrayList<Block>();
	}
	
	public long getLastClick() {
		return lastClick;
	}
	
	public void setLastClick(long lC) {
		lastClick = lC;
	}
	
	public State getState() {
		return state;
	}
	
	public void setState(State s) {
		state = s;
	}
	
	public enum State {
		IDLE,
		CLAIMING,
		RESIZING,
		DISPLAYELSE,
		DISPLAYOWN,
		CLAIMINGOVERLAPOWN,
		CLAIMINGOVERLAPELSE,
		RESIZINGOVERLAPOWN,
		RESIZINGOVERLAPELSE
	}
	
	public int getXDist() {
		return Math.abs(location1.getBlockX() - location2.getBlockX());
	}
	
	public String getResizingKey() {
		return resizingKey;
	}
	
	public void setResizingKey(String key) {
		resizingKey = key;
	}
	
	public int getArea() {
		return (getXDist() + 1) * (getZDist() + 1);
	}
	
	public int getZDist() {
		return Math.abs(location1.getBlockZ() - location2.getBlockZ());
	}
}


