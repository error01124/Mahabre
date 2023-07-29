package ru.d78boga.mahabre.entities.ai;

import net.minecraft.entity.ai.EntityAIAttackMelee;
import ru.d78boga.mahabre.entities.EntityBatu;

public class EntityAIBatuAttack extends EntityAIAttackMelee {
    private int raiseArmTicks;
    private EntityBatu entity;

    public EntityAIBatuAttack(EntityBatu entityIn, double speedIn, boolean longMemoryIn) {
        super(entityIn, speedIn, longMemoryIn);
        entity = entityIn;
    }

    @Override
    public void startExecuting() {
        super.startExecuting();
        raiseArmTicks = 0;
    }

    @Override
    public void resetTask() {
        super.resetTask();
        entity.setArmsRaised(false);
    }

    @Override
    public void updateTask() {
        super.updateTask();
        ++raiseArmTicks;

        if (this.raiseArmTicks >= 5 && this.attackTick < 10) {
            entity.setArmsRaised(true);
        } else {
            entity.setArmsRaised(false);
        }
    }
}