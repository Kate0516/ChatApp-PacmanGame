import './App.css';
import {BrowserRouter as Router, Route, Switch, useHistory} from "react-router-dom";
import {MainView} from "./ChatPage/mainView";
import {Landing} from "./Landing/landing";
import {useState} from "react";


function App() {
    const [loggedInUser, setLoggedInUser] = useState({
        username: "",
        token: "",
        age: 0,
        university: "",
        interest: [], // list of strings
        chatRooms: [], //  list of json objects
    });
    const [history, setHistory] = useState(useHistory());
    return (

        <div className="App">
            {loggedInUser.token === undefined || loggedInUser.token === "" ?
                <Landing loggedInUser={loggedInUser}
                         setLoggedInUser={setLoggedInUser}
                         history={history}
                         setHistory={setHistory}/> :
                <MainView loggedInUser={loggedInUser}
                          setLoggedInUser={setLoggedInUser}/>}
        </div>
    );
}

export default App;
