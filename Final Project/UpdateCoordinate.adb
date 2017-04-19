package body UpdateCoordinate is
   task body UpdateCoordinateTask is
      timer: Integer:=50;
      nextPeriod: 	Time := Clock;
      period: 		Time_Span := Milliseconds(timer);
      tmpCoordinate: COORDINATE;
      tmpSpeed: Float;
   begin
      tmpCoordinate.Y:=-40.0;
      CollisionAvoidanceSystem.carCoordinate.write(tmpCoordinate);
      loop
         nextPeriod := "+"(nextPeriod, period);
         delay until nextPeriod;
         CollisionAvoidanceSystem.getSpeed(tmpSpeed);
         if tmpSpeed > 0.0 then  --only update coordinates if car is moving
            CollisionAvoidanceSystem.carCoordinate.read(tmpCoordinate);

            tmpSpeed:=tmpSpeed*1000.0/3600.0;-- convert m/s to Km/h

            tmpCoordinate.Y:=tmpCoordinate.Y+((tmpSpeed/1000.0)*Float(timer));

            CollisionAvoidanceSystem.carCoordinate.write(tmpCoordinate);
         end if;
      end loop;
   end UpdateCoordinateTask;
end UpdateCoordinate;
