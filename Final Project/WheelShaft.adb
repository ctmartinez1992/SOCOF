package body WheelShaft is

   task body WheelShaftUpdateTask is
   begin
      loop
         select
            accept increaseRotation(value: in Float) do
               r := r + value;
               if r > 27.78 then
                  r := 27.77;
               end if;
            end increaseRotation;
         or
            accept decreaseRotation(value: in Float) do
               r := r - value;
               if r < 0.0 then
                  r := 0.0;
               end if;
            end decreaseRotation;
         end select;
      end loop;
   end WheelShaftUpdateTask;

   task body WheelShaftTask is
      nextPeriod: 	Ada.Real_Time.Time := Ada.Real_Time.Clock;
      period: 		Ada.Real_Time.Time_Span := Ada.Real_Time.Milliseconds(10);
   begin
      loop
         if r < 1.5 then
            nextPeriod := Ada.Real_Time."+"(nextPeriod,period);
            delay until nextPeriod;
         else
         nextPeriod := Ada.Real_Time."+"(nextPeriod, Ada.Real_Time.Milliseconds(Integer(500.0/r)));
            delay until nextPeriod;
            CollisionAvoidanceSystem.CASUpdate.rotationSignal;
         end if;
      end loop;
   end WheelShaftTask;

end WheelShaft;
