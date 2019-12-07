package edu.hbuas.chat.dao;

import edu.hbuas.chat.model.Friend;
import edu.hbuas.chat.model.User;
import edu.hbuas.chat.util.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class UserDao {
    /**
     * 数据库登陆方法，根据传入的用户名和密码查询返回一个用户对象
     *
     * @param user
     * @return
     */
    public User login(User user) {
        User u = null;
        try {
            Connection connection = JDBCUtils.getConnection();
            String sql = "select * from chatuser where username=? and password=?";
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setString(1, user.getUsername());
            pre.setString(2, user.getPassword());
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                u = new User();
                u.setUsername(rs.getString("username"));
                u.setNickname(rs.getString("nickname"));
                u.setSex(rs.getString("sex"));
                u.setAge(rs.getInt("age"));
                u.setImage(rs.getString("image"));
                u.setSignature(rs.getString("signature"));
                u.setStatus(rs.getInt("status"));
                Map<User, String> users = new HashMap<>();
                users = getAllFriends(user.getUsername());
                u.setFriends(users);
            }
            JDBCUtils.closeResource(connection, pre);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return u;
    }

    /**
     * 注册新用户的方法，根据传入的注册用户信息向数据库添加一个新的用户
     *
     * @param user
     * @return
     */
    public boolean register(User user) {
        boolean added = false;
        try {
            Connection connection = JDBCUtils.getConnection();
            String sql = "insert into chatuser(username,password,nickname,sex,age,image,signature,status) values(?,?,?,?,?,?,?,?)";
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setString(1, user.getUsername());
            pre.setString(2, user.getPassword());
            pre.setString(3, user.getNickname());
            pre.setString(4, user.getSex());
            pre.setInt(5, user.getAge());
            pre.setString(6, user.getImage());
            pre.setString(7, user.getSignature());
            pre.setInt(8,0);
            added = pre.executeUpdate() > 0 ? true : false;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return added;
    }

    /**
     * 检查用户是否已经存在,根据用户名查找用户
     *
     * @param username 用户对象
     * @return
     */
    public User checkUser(String username) {
        User users = null;
        try {
            // 获取数据库连接
            Connection connection = JDBCUtils.getConnection();
            // 判断用户是否存在的操作
            String sql = "select * from chatuser where username=?";
            // 预处理
            PreparedStatement ps = connection.prepareStatement(sql);
            // 传参
            ps.setString(1, username);
            // 执行
            ResultSet rs = ps.executeQuery();
            // 获取查询到的用户对象
            while (rs.next()) {
                users = new User();
                users.setUsername(rs.getString("username"));
                users.setNickname(rs.getString("nickname"));
                users.setSex(rs.getString("sex"));
                users.setAge(rs.getInt("age"));
                users.setImage(rs.getString("image"));
                users.setSignature(rs.getString("signature"));
                users.setStatus(rs.getInt("status"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    /**
     * 获取所有的好友
     * @param name
     * @return
     */
    public Map<User, String> getAllFriends(String name) {
        Map<User, String> users = new HashMap<>();
        try {
            Connection connection = JDBCUtils.getConnection();
            String sql = "select * from friend where username=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                // 这是获取到的
                String username = rs.getString("username");
                String friendType = rs.getString("friendType");
                String friendname = rs.getString("friendname");
                // 查询到的好友
                Friend friend = new Friend(username, friendType, friendname);
                // 根据查询到的这个好友得到该好友对应的用户属性
                User user;
                user = checkUser(friend.getFriendname());
                users.put(user, friendType);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }


    /**
     * 查找好友
     * @param friend
     * @return
     */
    public Friend checkFriend(Friend friend) {
        Friend friend1 = null;
        try {
            Connection connection = JDBCUtils.getConnection();
            String sql = "select * from friend where username =? and friendname = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, friend.getUsername());
            ps.setString(2, friend.getFriendname());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                friend1 = new Friend();
                friend1.setUsername(rs.getString("username"));
                friend1.setFriendType(rs.getString("friendType"));
                friend1.setFriendname(rs.getString("friendname"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return friend1;
    }

    /**
     * 添加好友
     * @param friend
     * @return
     */
    public String addFriend(Friend friend) {
        User user = checkUser(friend.getFriendname());
        Friend isFriend = checkFriend(friend);
        String result = "";
        if (user == null) {
            return "noPeople";
        } else if (isFriend == null) {
            try {
                Connection connection = JDBCUtils.getConnection();
                String sql = "insert into friend(username,friendType,friendname) values (?,?,?)";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, friend.getUsername());
                ps.setString(2, friend.getFriendType());
                ps.setString(3, friend.getFriendname());
                int r = ps.executeUpdate();
                if (r > 0) {
                    result = "success";
                } else {
                    result = "fail";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        } else {
            return "existed";
        }
    }
    /**
     * 更新所有用户状态
     * @return
     */
    public boolean updateAllUser() {
        try {
            // 获取数据库连接
            Connection connection = JDBCUtils.getConnection();
            // 更新用户状态操作
            String sql = "update chatuser set status=0";
            // 预处理
            PreparedStatement ps = connection.prepareStatement(sql);
            // 执行
            ps.executeUpdate();
            // 关闭资源
            JDBCUtils.closeResource(connection, ps);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
