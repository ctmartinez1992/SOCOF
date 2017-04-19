with 	Ada.Real_Time,
     	CollisionAvoidanceSystem,
     	Math,
     	VehicleConflictInfo,
        Ada.Float_Text_IO;

use 	CollisionAvoidanceSystem,
    	Math;

package VehicleDetectionSensor is

   --initialize position of the static object
   staticObject: COORDINATE := (0.0, 0.0);

   --Calcula a dist�ncia entre 2 coordenadas
   function distance(A: COORDINATE; B: COORDINATE) return Float;

   --Atualiza a sua posi��o e c�lcula a dist�ncia
   task VDSTask is
   end VDSTask;

end VehicleDetectionSensor;
