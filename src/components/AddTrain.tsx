import React, {useEffect, useState} from 'react';
import axios from "axios";

interface Train {
    type: string | undefined;
    number: number | undefined;
    platform: number | undefined;
    destination: string | undefined;
    arrivalTime: Date | undefined;
    departureTime: Date | undefined;
    delay: number | undefined;
}


function MainComponent() {

    const [type, setType] = useState<string>('');
    const [number, setNumber] = useState<number>();
    const [platform, setPlatform] = useState<number>();
    const [destination, setDestination] = useState<string>();
    const [arrivalTime, setArrivalTime] = useState<Date>();
    const [departureTime, setDepartureTime] = useState<Date>();
    const [delay, setDelay] = useState<number>();

    const handleUpload = () => {

        let data: Train = {
            arrivalTime: arrivalTime,
            delay: delay,
            departureTime: departureTime,
            destination: destination,
            number: number,
            platform: platform,
            type: type,

        }

        axios.post("http://localhost:8081/vlak", data)
        .catch((err) => console.log(err))
    }

    return (
        <div>
            <h1>Pridať/upraviť vlak</h1>
            <input placeholder={"Typ vlaku"} value={type} onChange={(e) => setType(e.target.value)}/>
            <input placeholder={"Číslo vlaku"} value={number} onChange={(e) => setNumber(Number(e.target.value))}/>
            <input placeholder={"Číslo nástupišta"} value={platform} onChange={(e) => setPlatform(Number(e.target.value))}/>
            <input placeholder={"Cieľ cesty"} value={destination} onChange={(e) => setDestination(e.target.value)}/>
            <input placeholder={"Typ vlaku"} value={type} onChange={(e) => setType(e.target.value)}/>
            <input placeholder={"Meškanie"} value={type} onChange={(e) => setType(e.target.value)}/>
            {/*<DateTimePicker*/}
            {/*    onChange={onChange}*/}
            {/*    value={value}*/}
            {/*/>*/}
            <button>Pridať vlak</button>
        </div>
    );
}

export default MainComponent;
