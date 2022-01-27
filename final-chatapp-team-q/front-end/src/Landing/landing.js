import './landing.css';
import React from "react";
import {Message, Icon} from "semantic-ui-react";
import {get, del, post} from "../util/index";
import {error, success} from "../util/message";

export class Landing extends React.Component {
    constructor(props) {
        super(props);
        localStorage.setItem("auth", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJxdWlja2NoYXQiLCJ1c2VybmFtZSI6InRlc3QifQ.ouJvL4eRZXfG9V_6PvGWby7ho4sN97hRrQ4hTNEJTig")
        this.state = {
            isLogginActive: true,
        };
        this.container = React.createRef();
    }

    componentDidMount() {
        this.rightSide.classList.add("right");
    }

    changeState() {
        const {isLogginActive} = this.state;
        if (isLogginActive) {
            this.rightSide.classList.remove("right");
            this.rightSide.classList.add("left");
        } else {
            this.rightSide.classList.remove("left");
            this.rightSide.classList.add("right");
        }
        this.setState(prevState => ({isLogginActive: !prevState.isLogginActive}));
    }


    render() {
        const {isLogginActive} = this.state;
        const current = isLogginActive ? "Register" : "Login";
        const currentActive = isLogginActive ? "login" : "register";
        return (
            <div>
                <div className="App">
                    <div className="login">
                        <div className="container" ref={ref => (this.container = ref)}>
                            {isLogginActive && (
                                <Login history={this.props.history} state={this.state}
                                       containerRef={ref => (this.current = ref)}
                                       setLoggedInUser={this.props.setLoggedInUser}
                                       loggedInUser={this.props.loggedInUser}/>
                            )}
                            {!isLogginActive && (
                                <Register history={this.props.history}
                                          containerRef={ref => (this.current = ref)} addUser={this.addUser}/>
                            )}
                        </div>
                        <RightSide
                            current={current}
                            currentActive={currentActive}
                            containerRef={ref => (this.rightSide = ref)}
                            onClick={this.changeState.bind(this)}
                        />
                    </div>
                </div>
            </div>
        );
    }
}

const RightSide = props => {
    return (
        <div
            className="right-side"
            ref={props.containerRef}
            onClick={props.onClick}
        >
            <div className="inner-container">
                <div className="text">{props.current}</div>
            </div>
        </div>
    );
};

class Login extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            user: {},
            password: '',
            username: '',
            isLoggedIn: false,
        }
        this.loginFunc = this.loginFunc.bind(this);
    }

    loginFunc() {
        let user = {
            username: this.state.username,
            password: this.state.password
        }
        post("/user/login", user, (res) => {
            if (res.success) {
                console.log(this.props)
                this.props.setLoggedInUser({
                    username: user.username,
                    token: res.payload.cookie
                })
                let token = res.payload.cookie
                get("/user/" + user.username, (res) => {
                    if (res.success) {
                        this.props.setLoggedInUser({
                            username: user.username,
                            token,
                            age: res.payload.age,
                            school: res.payload.school,
                            interests: res.payload.interests.join(', ')
                        })
                    } else {
                        console.log(res)
                        error(res.responseJSON.message)
                    }
                }, this.props.loggedInUser.token)
            } else {
                error(res.responseJSON.message)
            }
        });


    };

    render() {
        let message;
        if (this.state.isLoggedIn === true) {
            message = <Message icon error>
                <Icon name='warning'/>
                <Message.Content>
                    <Message.Header>Username Not Found</Message.Header>
                    The username you supplied was not found
                </Message.Content>
            </Message>
        }

        return (
            <div className="base-container" ref={this.props.containerRef}>
                <div className="header">Login</div>
                <div className="content">
                    <div className="form">
                        <div className="form-group">
                            <label htmlFor="username">Username</label>
                            <input type="text" name="username" id="username" placeholder="username"
                                   onChange={e => {
                                       this.setState({username: e.target.value})
                                   }}/>
                        </div>
                        <div className="form-group">
                            <label htmlFor="password">Password</label>
                            <input type="password" name="password" id="password" placeholder="password"
                                   onChange={e => {
                                       this.setState({password: e.target.value})
                                   }}/>
                        </div>
                    </div>
                </div>
                <div className="footer">
                    <button type="button" className="btn"
                            onClick={this.loginFunc}>
                        Login
                    </button>
                </div>
                {message}
            </div>
        );
    }
}

class Register extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            username: '',
            interest: '',
            school: '',
            age: '',
            password: '',
            confirmPassword: '',
            errorMsg: '',
            error: false,
            success: false
        }
        this.register = this.register.bind(this)

    }

    register() {
        let msg = ''
        let pwd = this.state.password;
        let confirmPwd = this.state.confirmPassword;

        if (this.state.username === '') {
            msg += 'user name cant be null \n'
            this.setState({errorMsg: msg, error: true, success: false})
            return
        }
        if (pwd !== confirmPwd) {
            msg += "password and password confirmation must be same\n"
            this.setState({errorMsg: msg, error: true, success: false})
            return
        }
        // /user/create
        //“Payload”:{
        //                         “username”: String,
        //                         “password”: String,
        //                         “age”: int,
        //                         “school”: String,
        //                         “interest”: []String
        //                     }
        let newUser = {
            id: 11,
            username: this.state.username,
            password: this.state.password,
            age: this.state.age,
            school: this.state.school,
            interest: [this.state.interest]
        }
        post("/user/create", newUser, (res) => {
            if (res.success) {
                success("User has ben register successfully!")
            }else{
                console.log(res)
                error(res.responseJSON.message)
            }
        });
    }


    render() {
        let emsg;
        if (this.state.error === true) {
            emsg = registerError(this.state.errorMsg);
        }
        let smsg;
        if (this.state.success === true) {
            smsg = registerSuccess();
        }
        return (
            <div className="base-container" ref={this.props.containerRef}>
                <div className="header">Register</div>
                <div className="content">
                    <form>
                        <div className="form">
                            <div className="form-group">
                                <label htmlFor="username">Username</label>
                                <input type="text" name="username" placeholder="username" onChange={e => {
                                    this.setState({username: e.target.value})
                                }}/>
                            </div>
                            <div className="form-group">
                                <label htmlFor="email">Interest</label>
                                <input type="email" name="email" placeholder="interest"
                                       onChange={e => {
                                           this.setState({interest: e.target.value})
                                       }}/>
                            </div>
                            <div className="form-group">
                                <label htmlFor="school">School</label>
                                <input type="text" name="school" placeholder="school" onChange={e => {
                                    this.setState({school: e.target.value})
                                }}/>
                            </div>
                            <div className="form-group">
                                <label htmlFor="age">Age</label>
                                <input type="text" name="school" placeholder="age" onChange={e => {

                                    this.setState({age: e.target.value})
                                }}/>
                            </div>
                            <div className="form-group">
                                <label htmlFor="password">Password</label>
                                <input type="password" name="password" placeholder="password" onChange={e => {
                                    this.setState({password: e.target.value})
                                }}/>
                            </div>
                            <div className="form-group">
                                <label htmlFor="password">Confirmed Password</label>
                                <input type="password" name="confirmPassword" placeholder="confirmedPassword"
                                       onChange={e => {
                                           this.setState({confirmPassword: e.target.value})
                                       }} />
                            </div>
                        </div>

                    </form>
                </div>
                <div className="footer">
                    <button type="button" className="btn" onClick={this.register}>
                        Register
                    </button>
                    {emsg}
                    {smsg}
                </div>
            </div>
        );
    }
}

const UserNotFound = () => {
    return (
        <Message icon error>
            <Icon name='warning'/>
            <Message.Content>
                <Message.Header>Username Not Found</Message.Header>
                The username you supplied was not found, therefore no repositories can be listed!
            </Message.Content>
        </Message>
    );
}

const registerSuccess = () => {
    return (
        <Message icon success>
            <Message.Content>
                <Message.Header>Register Success</Message.Header>
                Login with username and password!
            </Message.Content>
        </Message>
    );
}

const registerError = (msg) => {
    return (
        <Message icon error>
            <Icon name='warning'/>
            <Message.Content>
                <Message.Header>Register Error</Message.Header>
                {msg}
            </Message.Content>
        </Message>
    );
}


export default Landing;



