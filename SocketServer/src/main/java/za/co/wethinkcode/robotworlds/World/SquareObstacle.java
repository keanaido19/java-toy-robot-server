package za.co.wethinkcode.robotworlds.World;

import za.co.wethinkcode.robotworlds.Position;

public class SquareObstacle implements Obstacle {

    private final int bottomLeftX;
    private final int bottomLeftY;

    public SquareObstacle(int x, int y){
        this.bottomLeftX = x;
        this.bottomLeftY = y;
    }

    @Override
    public int getBottomLeftX() {
        return this.bottomLeftX;
    }

    @Override
    public int getBottomLeftY() {
        return this.bottomLeftY;
    }

    @Override
    public int getSize() {
        return 5;
    }

    @Override
    public boolean blocksPosition(Position position) {
        if(this.bottomLeftX <= position.getX() && position.getX() <= (this.bottomLeftX+ 3)){
            if(this.bottomLeftY <= position.getY() && position.getY()<= (this.bottomLeftY + 3))
                return true;
        }
        return false;
    }

    @Override
    public boolean blocksPath(Position a, Position b){
        if(a.getX() > b.getX()){
            int path = a.getX() - b.getX();
            for(int i = 0; i < path; i++){
                if(this.blocksPosition(new Position(b.getX()+i, b.getY()))){
                    return true;
                }
            }
        }
        else if(a.getX() < b.getX()){
            int path = b.getX() - a.getX();
            for(int i = 0; i < path; i++){
                if(this.blocksPosition(new Position(a.getX()+i, a.getY()))){
                    return true;
                }
            }
        }
        else if(a.getY() > b.getY()){
            int path = a.getY() - b.getY();
            for(int i = 0; i < path; i++){
                if(this.blocksPosition(new Position(b.getX(), b.getY()+ i))){
                    return true;
                }
            }
        }
        else if(a.getY() < b.getY()){
            int path = b.getY() - a.getY();
            for(int i = 0; i < path; i++){
                if(this.blocksPosition(new Position(a.getX(), a.getY()+ i))){
                    return true;
                }
            }
        }
        return false;
    }
}
