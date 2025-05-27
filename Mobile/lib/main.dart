import 'package:flutter/material.dart';
import 'auth_service.dart'; // Import AuthService
import 'home.dart'; // Import the HomePage

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Rackelni',
      theme: ThemeData(
        primarySwatch: Colors.green,
      ),
      home: const LoginPage(),
    );
  }
}

class LoginPage extends StatefulWidget {
  const LoginPage({super.key});

  @override
  State<LoginPage> createState() => _LoginPageState();
}

class _LoginPageState extends State<LoginPage> {
  final TextEditingController usernameController = TextEditingController();
  final TextEditingController passwordController = TextEditingController();
  final TextEditingController emailController = TextEditingController();
  final TextEditingController codeController = TextEditingController();
  final TextEditingController newPasswordController = TextEditingController();

  final AuthService _authService = AuthService(); // Instance of AuthService

  bool _isLoading = false;
  bool _isForgotPassword = false;
  bool _showRecoveryFields = false; // Show recovery fields (code + password)

  Future<void> _handleLogin() async {
    setState(() {
      _isLoading = true;
    });

    try {
      await _authService.login(
        usernameController.text,
        passwordController.text,
      );

      Navigator.pushReplacement(
        context,
        MaterialPageRoute(builder: (context) => const HomePage()),
      );
    } catch (e) {
      setState(() {
        _isLoading = false;
      });
      _showErrorDialog('Login Failed', e.toString());
    }
  }

  Future<void> _handleForgotPassword() async {
    setState(() {
      _isLoading = true;
    });

    try {
      await _authService.sendMailReset(emailController.text);

      setState(() {
        _isLoading = false;
        _showRecoveryFields = true; // Show fields for code and new password
      });

      _showInfoDialog('Password Recovery', 'An email has been sent for password recovery.');
    } catch (e) {
      setState(() {
        _isLoading = false;
      });
      _showErrorDialog('Error', e.toString());
    }
  }

  Future<void> _resetPassword() async {
    setState(() {
      _isLoading = true;
    });

    try {
      await _authService.changePassword(
        newPasswordController.text,
        int.parse(codeController.text),
      );

      setState(() {
        _isLoading = false;
        _isForgotPassword = false; // Return to login
        _showRecoveryFields = false;
      });

      _showInfoDialog('Success', 'Your password has been changed. Please log in with the new password.');
    } catch (e) {
      setState(() {
        _isLoading = false;
      });
      _showErrorDialog('Error', e.toString());
    }
  }

  void _showErrorDialog(String title, String message) {
    showDialog(
      context: context,
      builder: (context) => AlertDialog(
        title: Text(title),
        content: Text(message),
        actions: [
          TextButton(
            onPressed: () => Navigator.pop(context),
            child: const Text('OK'),
          ),
        ],
      ),
    );
  }

  void _showInfoDialog(String title, String message) {
    showDialog(
      context: context,
      builder: (context) => AlertDialog(
        title: Text(title),
        content: Text(message),
        actions: [
          TextButton(
            onPressed: () => Navigator.pop(context),
            child: const Text('OK'),
          ),
        ],
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.white,
      body: Stack(
        children: [
          // Top Green Background
          Container(
            height: MediaQuery.of(context).size.height * 0.5,
            width: MediaQuery.of(context).size.height * 0.6,
            decoration: const BoxDecoration(
              gradient: LinearGradient(
                colors: [Color.fromARGB(255, 7, 102, 9), Color.fromARGB(255, 149, 204, 151)],
                begin: Alignment.topCenter,
                end: Alignment.bottomCenter,
              ),
            ),
            child: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                const Text(
                  'WELCOME!',
                  style: TextStyle(
                    fontSize: 20,
                    fontWeight: FontWeight.normal,
                    color: Colors.white,
                  ),
                ),
                const SizedBox(height: 20),
                Image.asset(
                  'assets/NormalWhite.png',
                  height: 200,
                  width: 350,
                ),
              ],
            ),
          ),

          // Login or Forgot Password Form
          Align(
            alignment: Alignment.bottomCenter,
            child: SingleChildScrollView(
              child: Padding(
                padding: const EdgeInsets.only(top: 240.0),
                child: Column(
                  children: [
                    Container(
                      margin: const EdgeInsets.symmetric(horizontal: 20),
                      padding: const EdgeInsets.all(20),
                      child: Column(
                        children: [
                          if (!_isForgotPassword) ...[
                            // Login Form
                            TextField(
                              controller: usernameController,
                              decoration: InputDecoration(
                                hintText: 'Username or Email',
                                border: OutlineInputBorder(
                                  borderRadius: BorderRadius.circular(10.0),
                                ),
                              ),
                            ),
                            const SizedBox(height: 20),
                            TextField(
                              controller: passwordController,
                              obscureText: true,
                              decoration: InputDecoration(
                                hintText: 'Password',
                                border: OutlineInputBorder(
                                  borderRadius: BorderRadius.circular(10.0),
                                ),
                              ),
                            ),
                            const SizedBox(height: 20),
                            _isLoading
                                ? const CircularProgressIndicator()
                                : ElevatedButton(
                                    onPressed: _handleLogin,
                                    style: ElevatedButton.styleFrom(
                                      backgroundColor: Colors.green,
                                      padding: const EdgeInsets.symmetric(horizontal: 100, vertical: 15),
                                      shape: RoundedRectangleBorder(
                                        borderRadius: BorderRadius.circular(10.0),
                                      ),
                                    ),
                                    child: const Text(
                                      'LOG IN',
                                      style: TextStyle(fontSize: 16, color: Colors.white),
                                    ),
                                  ),
                            const SizedBox(height: 20),
                            TextButton(
                              onPressed: () {
                                setState(() {
                                  _isForgotPassword = true;
                                });
                              },
                              child: const Text(
                                'Forgot Password?',
                                style: TextStyle(
                                  color: Colors.blue,
                                  decoration: TextDecoration.underline,
                                ),
                              ),
                            ),
                          ] else ...[
                            // Forgot Password Form
                            TextField(
                              controller: emailController,
                              decoration: InputDecoration(
                                hintText: 'Enter your email',
                                border: OutlineInputBorder(
                                  borderRadius: BorderRadius.circular(10.0),
                                ),
                              ),
                            ),
                            const SizedBox(height: 20),
                            if (_showRecoveryFields) ...[
                              TextField(
                                controller: codeController,
                                decoration: InputDecoration(
                                  hintText: 'Enter recovery code',
                                  border: OutlineInputBorder(
                                    borderRadius: BorderRadius.circular(10.0),
                                  ),
                                ),
                              ),
                              const SizedBox(height: 20),
                              TextField(
                                controller: newPasswordController,
                                obscureText: true,
                                decoration: InputDecoration(
                                  hintText: 'Enter new password',
                                  border: OutlineInputBorder(
                                    borderRadius: BorderRadius.circular(10.0),
                                  ),
                                ),
                              ),
                              const SizedBox(height: 20),
                              ElevatedButton(
                                onPressed: _resetPassword,
                                style: ElevatedButton.styleFrom(
                                  backgroundColor: Colors.green,
                                  padding: const EdgeInsets.symmetric(horizontal: 100, vertical: 15),
                                  shape: RoundedRectangleBorder(
                                    borderRadius: BorderRadius.circular(10.0),
                                  ),
                                ),
                                child: const Text(
                                  'RESET PASSWORD',
                                  style: TextStyle(fontSize: 16, color: Colors.white),
                                ),
                              ),
                            ] else ...[
                              ElevatedButton(
                                onPressed: _handleForgotPassword,
                                style: ElevatedButton.styleFrom(
                                  backgroundColor: Colors.green,
                                  padding: const EdgeInsets.symmetric(horizontal: 100, vertical: 15),
                                  shape: RoundedRectangleBorder(
                                    borderRadius: BorderRadius.circular(10.0),
                                  ),
                                ),
                                child: const Text(
                                  'SEND EMAIL',
                                  style: TextStyle(fontSize: 16, color: Colors.white),
                                ),
                              ),
                            ],
                            const SizedBox(height: 20),
                            TextButton(
                              onPressed: () {
                                setState(() {
                                  _isForgotPassword = false;
                                  _showRecoveryFields = false;
                                });
                              },
                              child: const Text(
                                'Back to Login',
                                style: TextStyle(
                                  color: Colors.blue,
                                  decoration: TextDecoration.underline,
                                ),
                              ),
                            ),
                          ],
                        ],
                      ),
                    ),
                  ],
                ),
              ),
            ),
          ),
        ],
      ),
    );
  }
}
