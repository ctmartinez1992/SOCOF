
package body Brake is

   task body BrakeTask is
   begin
      loop

          accept brake(v: in Voltage;fric: in Float) do
            WheelShaft.WheelShaftUpdateTask.decreaseRotation(Float(v)*fric);
         end brake;

      end loop;
   end BrakeTask;
end Brake;
