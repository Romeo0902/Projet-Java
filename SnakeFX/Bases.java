public class Bases extends GameElement {
    public int woodStockBase;
    private final Coordinate coordSpawn;
    private final static int emptyWoodStockBase = 0;
    private final Coordinate coordStock;
    public static int north = 1;
    public static int south = 2;

    public static final Coordinate coordSpawnNorth = new Coordinate((Controller.ROWS / 2), (Controller.northBaseBot));
    public static final Coordinate coordSpawnSouth = new Coordinate((Controller.ROWS / 2), (Controller.southBaseTop));
    public static final Coordinate coordStockNorth = new Coordinate((Controller.basesRight), (Controller.northBaseBot)-3);
    public static final Coordinate coordStockSouth = new Coordinate((Controller.basesLeft), (Controller.southBaseTop)+3);
    
        public Bases(Coordinate coordSpawn, int woodStockBase, Coordinate coordStock) {
            this.coordSpawn = coordSpawn;
            this.woodStockBase = woodStockBase;
            this.coordStock = coordStock;
        }
    
        public Coordinate getCoordSpawn() {
            return this.coordSpawn;
        }
    
        public Coordinate getCoordStockWood() {
            return this.coordStock;
        }
    
        public int getWoodStockBase() {
            return this.woodStockBase;
        }
    
        public static int getEmptyWoodStockBase() {
            return emptyWoodStockBase;
        }
    
        public void setWoodStock(int woodStockBase) {
            this.woodStockBase = woodStockBase;
        }
    
        // génération des unités
        public static void collectorsSpawn(int side, int ROWS, int northBaseBot, int southBaseTop) {
            Collectors collector = new Collectors(null,
                    Collectors.getMaxHp(),
                    Collectors.getDamage(), null, Collectors.getEmptyWoodStock());
            if (side == north) {
            collector.setSide("north");
            collector.setCoord(coordSpawnNorth);
        } else if (side == south) {
            collector.setSide("south");
            collector.setCoord(coordSpawnSouth);
        }
        Controller.listOfCollectors.add(collector);
        Controller.listOfGameElements.add(collector);
        Controller.listOfUnits.add(collector);
    }

    // génération des unités
    public static void desertersSpawn(int side, int ROWS, int northBaseBot, int southBaseTop) {

        Deserters deserter = new Deserters(null, Deserters.getBonusAgainstPikemen(),
                Deserters.getBonusAgainstDeserters(), Deserters.getMaxHp(),
                Deserters.getDamage(), null);
        if (side == north) {
            deserter.setSide("north");
            deserter.setCoord(coordSpawnNorth);
            Controller.listOfUnitsNorth.add(deserter);
        } else if (side == south) {
            deserter.setSide("south");
            deserter.setCoord(coordSpawnSouth);
        }
        // listOfUnitsSouth, northBaseBot, southBaseTop);

        Controller.listOfDeserters.add(deserter);
        Controller.listOfGameElements.add(deserter);
        Controller.listOfUnits.add(deserter);
    }

    // génération des unités
    public static void horsemenSpawn(int side, int ROWS, int northBaseBot, int southBaseTop) {

        Horsemen horseman = new Horsemen(null, Horsemen.getDamage(), Horsemen.getBonus(),
                Horsemen.getMaxHp(), null);
        if (side == north) {
            horseman.setSide("north");
            horseman.setCoord(coordSpawnNorth);
        } else if (side == south) {
            horseman.setSide("south");
            horseman.setCoord(coordSpawnSouth);
        }
        Controller.listOfHorsemen.add(horseman);
        Controller.listOfGameElements.add(horseman);
        Controller.listOfUnits.add(horseman);
    }

    // génération des unités
    public static void pikemenSpawn(int side, int ROWS, int northBaseBot, int southBaseTop) {

        Pikemen pikeman = new Pikemen(null, Pikemen.getMaxHp(), Pikemen.getBonus(),
                Pikemen.getDamage(), null);
        if (side == north) {
            pikeman.setSide("north");
            pikeman.setCoord(coordSpawnNorth);
        } else if (side == south) {
            pikeman.setSide("south");
            pikeman.setCoord(coordSpawnSouth);
        }
        Controller.listOfPikemen.add(pikeman);
        Controller.listOfGameElements.add(pikeman);
        Controller.listOfUnits.add(pikeman);
    }
}
