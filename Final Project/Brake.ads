with 	Ada.Numerics.Elementary_Functions,
     	Ada.Numerics.Generic_Elementary_Functions,
     	Ada.Text_IO,
     	Ada.Integer_Text_IO,
     	Math,
     	WheelShaft;

use 	Ada.Numerics.Elementary_Functions,
    	Math;

package Brake is

   --Voltage é um valor entre 1 e 8
   type Voltage is range 1..8;

   --Dynamic package para a voltagem (Voltage)
   package Voltage_IO is new Ada.Text_IO.Enumeration_IO(Voltage);

   task BrakeTask is
       --Receives the voltage value and tells the WheelShaft so start breaking
      entry brake(v: in Voltage;fric: in Float);
   end BrakeTask;
end Brake;
