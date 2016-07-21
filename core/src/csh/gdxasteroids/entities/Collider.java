package csh.gdxasteroids.entities;

public interface Collider
{
    public void collisionAction();
    public boolean canCollide(Entity entity);
}