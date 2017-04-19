with 	Ada.Text_IO,
     Ada.Float_Text_IO,
     Ada.Real_Time,
     CollisionAvoidanceSystem;

package WheelShaft is



   task WheelShaftUpdateTask is
      entry increaseRotation(value: in Float);
      entry decreaseRotation(value: in Float);
   end WheelShaftUpdateTask;

   task WheelShaftTask is
   end WheelShaftTask;
private
   r:	Float := 0.0;
end WheelShaft;
