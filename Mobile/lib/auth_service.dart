import 'dart:convert';
import 'package:http/http.dart' as http;

class AuthService {
  final String baseUrl = "http://10.0.2.2:8989"; // Backend URL for Android emulator
  final String changePasswordUrl = "http://10.0.2.2:8989/oauth/changePassword"; // Change password URL

  Future<Map<String, dynamic>> login(String username, String password) async {
    final url = Uri.parse('$baseUrl/oauth/token?grant_type=password&username=$username&password=$password');

    // Basic Auth credentials: client:TESTtest1234
    final String basicAuth = 'Basic ' + base64Encode(utf8.encode('client:TESTtest1234'));

    try {
      final response = await http.post(
        url,
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded', // Required content type
          'Authorization': basicAuth, // Add Basic Auth header
        },
      );

      if (response.statusCode == 200) {
        return jsonDecode(response.body); // Successful login
      } else {
        throw Exception('Failed to login: ${response.body}');
      }
    } catch (e) {
      throw Exception('Error: $e');
    }
  }

  Future<void> sendMailReset(String email) async {
    // URL with the email query parameter
    final url = Uri.parse('$baseUrl/oauth/resetPassword?email=$email');

    try {
      final response = await http.post(
        url,
        headers: {'Content-Type': 'application/json'}, // Add Content-Type header
      );

      if (response.statusCode != 200) {
        throw Exception('Failed to send reset email: ${response.body}');
      }
    } catch (e) {
      throw Exception('Error: $e');
    }
  }

  Future<void> changePassword(String newPassword, int token) async {
    final url = Uri.parse(changePasswordUrl);
    final body = jsonEncode({
      'newPassword': newPassword,
      'token': token,
    });

    try {
      final response = await http.post(
        url,
        headers: {
          'Content-Type': 'application/json', // Specify JSON content
        },
        body: body,
      );

      if (response.statusCode != 200) {
        throw Exception('Failed to change password: ${response.body}');
      }
    } catch (e) {
      throw Exception('Error: $e');
    }
  }
}
