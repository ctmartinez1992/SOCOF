with 	Ada.Text_IO,
        Ada.Real_Time,
        WheelShaft;

use     Ada.Text_IO;

package Accelerator is

   --Limita-se a receber o accept, não faz processamento
   task AcceleratorUpdateTask is
      --Changes the on variable to True or False, if true, it's accelerating; if false, it's slowing down.
      entry changeState(value:in Boolean);
   end AcceleratorUpdateTask;

   procedure getState(value: out Boolean);

   --Apenas faz o processamento (simulação) do acelerador
   task AcceleratorTask is
   end AcceleratorTask;

   --private
   --on = True is accelerating; on = False is NOT accelerating

   on: Boolean := False; --Private so other tasks dont access it
end Accelerator;
