package com.danilyanich.gameobject;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.danilyanich.input.KindOfInput;

public final class Fish extends GameObject {
    private final float jump_velocity;
    public boolean isDead = false;
    private Circle collider;
    private Vector2 acceleration;
    private Vector2 velocity = new Vector2(0f, 0f);

    private float max_velocity;

    public Fish(Vector2 pos, Vector2 size, Vector2 acceleration, float jump_velocity, float max_velocity, boolean active) {
        super(pos, size, active);
        this.max_velocity = max_velocity;
        this.acceleration = acceleration;
        this.jump_velocity = jump_velocity;
        collider = new Circle(this.position, 4);
    }

    @Override
    public void update(float delta) {
        velocity.add(acceleration.cpy().scl(delta));
        if (velocity.y > max_velocity)
            velocity.y = max_velocity;
        position.add(velocity.cpy().scl(delta));
        this.rotation = (40 / max_velocity) * this.velocity.y;

        this.collider.set(position.x + 4, position.y + 5, 4);
    }

    @Override
    public void Input(KindOfInput in, int screenX, int screenY, int pointer, int button) {
        if (in == KindOfInput.touchDown)
            this.velocity.y = jump_velocity;
    }

    public Circle getCollider() {
        return collider;
    }
}