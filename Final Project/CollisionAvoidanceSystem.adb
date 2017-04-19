with Brake,
     Accelerator;

package body CollisionAvoidanceSystem is

   function getVoltage(Velocity: in Float; U: in Float; Dist: in Float) return Voltage is
      Res: 	Float := 0.0;
      Const: 	Float := 9.81;
   begin
      Res := 8.0 * (((Velocity / 3.6) ** 2) / (2.0 * Const * Dist * U));
      if(Res <= 1.0) then
         Res :=1.0;
      elsif(Res >= 8.0) then
         Res :=8.0;
      end if;
      return Voltage(Res);
   end getVoltage;

   function getFriction(fValue: in Friction) return Float is
      Res: 	Float := 0.0;
   begin
      case fValue is
      when DryAsphalt => Res := 0.7;
      when WetAsphalt => Res := 0.55;
      when DryConcrete => Res := 0.8;
      when WetConcrete => Res := 0.5;
      when Snow => Res := 0.3;
      when Ice => Res := 0.15;
      end case;

      return Res;
   end getFriction;

   procedure getSpeed(s: out Float) is
   begin
      s := speed;
   end getSpeed;

   procedure getDistance(d: out Float) is
   begin
      d := distance;
   end getDistance;

   protected body ProtectedCoordinate is
      procedure read(curCoord: out COORDINATE) is
      begin
         curCoord := coord;
      end read;

      procedure write(newCoord: in COORDINATE) is
      begin
         coord := newCoord;
      end write;
   end ProtectedCoordinate;

   procedure updateDistance(d: in Float; yD: in Float) is
      begin
         distance := d;
         yDistance := yD;
   end;

   task body CASUpdate is
      stopTime: 	Ada.Real_Time.Time := Ada.Real_Time.Clock;
      startTime:	Ada.Real_Time.Time := Ada.Real_Time.Clock;
      timeSpan:		Ada.Real_Time.Time_Span;
   begin
      loop
         select
            accept rotationSignal do
               stopTime := Ada.Real_Time.Clock;
               timeSpan := Ada.Real_Time."-"(stopTime, startTime);
               speed := (500.0 / Float(Ada.Real_Time.To_Duration(timeSpan))) * 3.6 * 0.3 * 0.001;
               --Put(speed, 6, 3, 0);
               startTime := Ada.Real_Time.Clock;
            end rotationSignal;

         or
            delay 0.5;
            speed := 0.0;

         end select;
      end loop;
   end CASUpdate;

   task body CAS is
      --Distância necessária para parar o carro
      stopDistance:	Float := 0.0;

      --A distância atual
      currentDistance:	Float := 0.0;

      --A distância entre ys atual
      currentYDistance:	Float := 0.0;

      --A diferença entre a distância do carro e do objeto
      diffDistance:	Float := 0.0;

      --For main loop counter
      c:		Integer := 0;

      --Checks after the main loop if it needs to break
      needsToBreak:	Boolean := False;

      --The necessary voltage to safely break the car
      voltageToBreak:	Voltage;

      --Fricção usada
      usedFriction:	Float;

      --De meio em meio segundo, processa o acelerador
      nextPeriod: 	Ada.Real_Time.Time := Ada.Real_Time.Clock;
      period: 		Ada.Real_Time.Time_Span := Ada.Real_Time.Milliseconds(100);

      tmp:		Integer;

   begin
      --Obtém o valor da fricção
      usedFriction := getFriction(f);
      Ada.Text_IO.Put("Friction: ");
      Ada.Float_Text_IO.Put(usedFriction, 6, 3, 0);
      Ada.Text_IO.New_Line;


      Ada.Integer_Text_IO.Get(tmp);

      loop
         nextPeriod := Ada.Real_Time."+"(nextPeriod, period);
         delay until nextPeriod;

         --Vai buscar os valores necessários para computar a decisão a fazer
         --VehicleDetectionSensor.getDistance(currentDistance);
         currentDistance := distance;
         currentYDistance := yDistance;

         if currentYDistance > 0.0 then
            Ada.Text_IO.New_Line;
            Ada.Text_IO.New_Line;
            Put("distance: ");
            Put(currentDistance, 6, 3, 0);
            Put("m");
            New_Line;

            if currentDistance < 5.0 then
               Ada.Text_IO.Put("AUTOMATIC: BRAKING DOWN!!");
               Ada.Text_IO.New_Line;
               --Iniciar valores de controlo
               c := 0;
               needsToBreak := False;


               --Calcula a voltagem necessária para parar
               voltageToBreak := getVoltage(speed, usedFriction, currentDistance);

               Ada.Text_IO.Put("Voltage: ");
               Ada.Integer_Text_IO.Put(Integer(voltageToBreak));
               Ada.Text_IO.New_Line;

               Brake.BrakeTask.brake(Brake.Voltage(voltageToBreak),usedFriction);
               Accelerator.AcceleratorUpdateTask.changeState(False);

            elsif currentDistance <= 6.0 and currentDistance >= 5.0  then
               needsToBreak := false;
               Accelerator.AcceleratorUpdateTask.changeState(False);
               Brake.BrakeTask.brake(Brake.Voltage(1),usedFriction);
               Ada.Text_IO.Put("WARNING: SLOW DOWN!");
               Ada.Text_IO.New_Line;
               Ada.Text_IO.New_Line;
               Ada.Text_IO.New_Line;
            elsif currentDistance > 6.0 then
               declare
                  estado:Boolean;
                  velocidade: Float;
               begin
                  Put("speed");
                  getSpeed(velocidade);
                  Put(velocidade, 6, 3, 0);
                  Put("Km/h");
                  Ada.Text_IO.New_Line;
                  new_line;
                  -- Accelerator.getState(estado);
                  estado:= Accelerator.on;
                  if estado = false then
                     Accelerator.AcceleratorUpdateTask.changeState(True);
                  end if;
               end;
            end if;
         else
            Ada.Text_IO.Put("VEHICLE HAS COLLIDED!!! ABORT! ABORT! ABORT!");
            Ada.Text_IO.New_Line;
         end if;
      end loop;
   end CAS;
end CollisionAvoidanceSystem;
