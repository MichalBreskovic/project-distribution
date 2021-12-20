import React, {useEffect, useState} from 'react';
import axios from "axios";

interface Train {
    type: string;
    number: number;
    platform: number;
    destination: string;
    arrivalTime: Date;
    departureTime: Date;
    delay: number;
}


function MainComponent() {

    const [platform, setPlatform] = useState<number>();
    const [trains, setTrains] = useState<Train[]>();

    useEffect(() => {
        setInterval(() => {
            axios.get("http://localhost:8081/nastupiste")
            .then(response => {
                let newTrains: Train[] = [];
                response.data?.forEach((data: Train) => {
                    console.log(data)
                    setPlatform(data.platform)
                    newTrains.push(data)
                })
                setTrains(newTrains)
            })
        }, 2000)
    })

    return (
        <div>
            <h1>Nástupište {platform}</h1>
            <div style={{display: "flex", justifyContent: "center", alignItems: "center"}}>
                <table>
                    <tr>
                        <th>Vlak</th>
                        <th>Čas príchodu</th>
                        <th>Čas odchodu</th>
                        <th>Cieľová stanica</th>
                        <th>Meškanie</th>
                    </tr>
                    {trains && trains.map((train: Train) => {
                        return (
                            <>
                                <tr>
                                    <td>{train.type}{train.number}</td>
                                    <td>{train.arrivalTime}</td>
                                    <td>{train.departureTime}</td>
                                    <td>{train.destination}</td>
                                    <td>{train.delay} m</td>
                                </tr>
                            </>
                        )
                    })}
                </table>
            </div>
        </div>
    );
}

export default MainComponent;
