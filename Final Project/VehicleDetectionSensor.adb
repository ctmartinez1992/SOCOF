package body VehicleDetectionSensor is

   function distance(A: COORDINATE; B: COORDINATE) return Float is
      distance: 	Float;
      quadrado_x: 	Float := 0.0;
      quadrado_y: 	Float := 0.0;
   begin
      quadrado_x := Pow(A.X - B.X, 2);
      quadrado_y := Pow(A.Y - B.Y, 2);

      distance := Math.Sqrt(quadrado_x + quadrado_y, 2);

      return distance;
   end distance;

   task body VDSTask is
      --De 1/10 de segundo em 1/10 de segundo, processa o acelerador
      nextPeriod: 	Ada.Real_Time.Time := Ada.Real_Time.Clock;
      period: 		Ada.Real_Time.Time_Span := Ada.Real_Time.Milliseconds(50);

      carCoord: 	COORDINATE;
      objCoord:		COORDINATE;

      dist: 		Float := 0.0;
      yDist:		Float := 0.0;
   begin
      loop
         nextPeriod := Ada.Real_Time."+"(nextPeriod, period);
         delay until nextPeriod;

         carCoordinate.read(carCoord); --get coordinate of the car
         VehicleConflictInfo.objectCoordinate.read(objCoord); --get coordinate of the object

         dist := distance(carCoord, staticObject); 	--distance with static object
         --dist := distance(carCoord, objCoord); 	--distance with moving object

         yDist := staticObject.Y - carCoord.Y;   	--Y distance with static object
         --yDist := objCoord.Y - carCoord.Y;		--Y distance with moving object

         --Ada.Float_Text_IO.Put(yDist, 6, 3, 0);

         CollisionAvoidanceSystem.updateDistance(dist, yDist);
      end loop;
   end VDSTask;

end VehicleDetectionSensor;
