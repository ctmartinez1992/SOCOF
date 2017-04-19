package body Accelerator is

   task body AcceleratorUpdateTask is
   begin
      loop
         accept changeState(value: Boolean) do
            on := value;
         end changeState;
      end loop;
   end AcceleratorUpdateTask;

   task body AcceleratorTask is
      --De meio em meio segundo, processa o acelerador
      nextPeriod: 	Ada.Real_Time.Time := Ada.Real_Time.Clock;
      period: 		Ada.Real_Time.Time_Span := Ada.Real_Time.Milliseconds(100);
   begin
      loop
         nextPeriod := Ada.Real_Time."+"(nextPeriod, period);
         delay until nextPeriod;
         if on = True then
            WheelShaft.WheelShaftUpdateTask.increaseRotation(0.3);
         end if;
      end loop;
   end AcceleratorTask;

   procedure getState(value: out Boolean) is
   begin
      value := on;
   end getState;
end Accelerator;
