package com.xlf.lwtm

import com.google.gson.Gson
import com.google.gson.internal.LinkedTreeMap
import java.io.File
import java.sql.*
import java.time.Instant
import kotlin.system.exitProcess

class Database {
    private var connection: Connection? = null

    fun checkConnect() {
        val connectInfo =
            Gson().fromJson(File("config.json").readText(), HashMap::class.java) as HashMap<String, String>

        try {
            Class.forName("com.mysql.cj.jdbc.Driver")
            connection = DriverManager.getConnection(
                "jdbc:mysql://${connectInfo["mysql_url"]}/${connectInfo["mysql_database"]}",
                connectInfo["mysql_user"],
                connectInfo["mysql_password"]
            )
        } catch (e: Exception) {
            println("[ERROR] 数据库连接失败")
            println("[ERROR] ${e.message}")
            exitProcess(0)
        }
    }

    fun insertUsers(): Boolean {
        Data.usersJson.forEach { hashMap ->
            try {
                connection!!.prepareStatement("INSERT INTO wl_users (display_name, email, password, type, label, url, avatar, github, twitter, facebook, google, weibo, qq, `2fa`, createdAt, updatedAt, user_id) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)")
                    .also { it.setString(1, hashMap["display_name"] as String?) }
                    .also { it.setString(2, hashMap["email"] as String) }
                    .also { it.setString(3, hashMap["password"] as String) }
                    .also { it.setString(4, hashMap["type"] as String) }
                    .also { it.setString(5, hashMap["label"] as String?) }
                    .also { it.setString(6, hashMap["url"] as String?) }
                    .also { it.setString(7, hashMap["avatar"] as String?) }
                    .also { it.setString(8, hashMap["github"] as String?) }
                    .also { it.setString(9, hashMap["twitter"] as String?) }
                    .also { it.setString(10, hashMap["facebook"] as String?) }
                    .also { it.setString(11, hashMap["google"] as String?) }
                    .also { it.setString(12, hashMap["weibo"] as String?) }
                    .also { it.setString(13, hashMap["qq"] as String?) }
                    .also { it.setString(14, hashMap["2fa"] as String?) }.also {
                        it.setTimestamp(
                            15, Timestamp(Instant.parse(hashMap["createdAt"] as String).toEpochMilli())
                        )
                    }.also {
                        it.setTimestamp(
                            16, Timestamp(Instant.parse(hashMap["updatedAt"] as String).toEpochMilli())
                        )
                    }.also { it.setString(17, hashMap["objectId"] as String?) }.executeUpdate()
            } catch (e: Exception) {
                println("[ERROR] 数据库插入失败")
                println("[ERROR] ${e.message}")
                throw e
            }
        }
        return true
    }

    fun insertCounter() {
        Data.counterJson.forEach { hashMap ->
            connection!!.prepareStatement("INSERT INTO wl_counter (time, reaction0, reaction1, reaction2, reaction3, reaction4, reaction5, reaction6, reaction7, reaction8, url, createdAt, updatedAt) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)")
                .also { it.setString(1, hashMap["key"] as String) }.also { it.setInt(2, hashMap["value"] as Int) }
                .executeUpdate()
        }
    }

    fun insertComment(): Boolean {
        Data.commentJson.forEach { hashMap ->
            try {
                connection!!.prepareStatement("INSERT INTO wl_comment (user_id, comment, insertedAt, ip, link, mail, nick, pid, rid, sticky, status, `like`, ua, url, createdAt, updatedAt, object_id) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)")
                    .also { data ->
                        if (hashMap["user_id"] == null) {
                            data.setNull(1, Types.INTEGER)
                        } else {
                            val getData =
                                connection!!.prepareStatement("SELECT id, user_id FROM wl_users WHERE user_id = ?")
                                    .also { it.setString(1, hashMap["user_id"] as String) }.executeQuery()
                                    .also { it.next() }
                            if (getData.getString("user_id") == hashMap["user_id"]) {
                                data.setInt(1, getData.getInt("id"))
                            }
                        }
                    }.also {
                        if (hashMap["comment"] == null) {
                            it.setNull(2, Types.VARCHAR)
                        } else {
                            it.setString(2, hashMap["comment"] as String)
                        }
                    }.also {
                        val insertAt = hashMap["insertedAt"] as LinkedTreeMap<*, *>
                        it.setTimestamp(
                            3, Timestamp(Instant.parse(insertAt["iso"] as String).toEpochMilli())
                        )
                    }.also { it.setString(4, hashMap["ip"] as String) }.also {
                        if (hashMap["link"] == null) {
                            it.setNull(5, Types.VARCHAR)
                        } else {
                            it.setString(5, hashMap["link"] as String)
                        }
                    }.also {
                        if (hashMap["mail"] == null) {
                            it.setNull(6, Types.VARCHAR)
                        } else {
                            it.setString(6, hashMap["mail"] as String)
                        }
                    }.also {
                        if (hashMap["nick"] == null) {
                            it.setNull(7, Types.VARCHAR)
                        } else {
                            it.setString(7, hashMap["nick"] as String)
                        }
                    }.also { comment ->
                        // 回复相关
                        if (hashMap["pid"] == null) {
                            comment.setNull(8, Types.INTEGER)
                        } else {
                            val getData =
                                connection!!.prepareStatement("SELECT id, object_id FROM wl_comment WHERE object_id = ?")
                                    .also { it.setString(1, hashMap["pid"] as String) }.executeQuery()
                                    .also { it.next() }
                            try {
                                if ((getData.getString("object_id") ?: null) == hashMap["pid"]) {
                                    comment.setInt(8, getData.getInt("id"))
                                }
                            } catch (e: SQLException) {
                                comment.setNull(8, Types.INTEGER)
                            }
                        }
                    }.also { comment ->
                        // 回复相关
                        if (hashMap["rid"] == null) {
                            comment.setNull(9, Types.INTEGER)
                        } else {
                            val getData =
                                connection!!.prepareStatement("SELECT id, object_id FROM wl_comment WHERE object_id = ?")
                                    .also { it.setString(1, hashMap["rid"] as String) }.executeQuery()
                                    .also { it.next() }
                            try {
                                if ((getData.getString("object_id") ?: null) == hashMap["rid"]) {
                                    comment.setInt(9, getData.getInt("id"))
                                }
                            } catch (e: SQLException) {
                                comment.setNull(9, Types.INTEGER)
                            }
                        }
                    }.also {
                        if (hashMap["sticky"] == null) {
                            it.setNull(10, Types.INTEGER)
                        } else {
                            it.setInt(10, hashMap["sticky"] as Int)
                        }
                    }.also { it.setString(11, hashMap["status"] as String) }.also {
                        if (hashMap["like"] == null) {
                            it.setNull(12, Types.INTEGER)
                        } else {
                            it.setInt(12, (hashMap["like"] as Double).toInt())
                        }
                    }.also {
                        if (hashMap["ua"] == null) {
                            it.setNull(13, Types.VARCHAR)
                        } else {
                            it.setString(13, hashMap["ua"] as String)
                        }
                    }.also {
                        if (hashMap["url"] == null) {
                            it.setNull(14, Types.VARCHAR)
                        } else {
                            it.setString(14, hashMap["url"] as String)
                        }
                    }.also {
                        it.setTimestamp(
                            15, Timestamp(Instant.parse(hashMap["createdAt"] as String).toEpochMilli())
                        )
                    }.also {
                        it.setTimestamp(
                            16, Timestamp(Instant.parse(hashMap["updatedAt"] as String).toEpochMilli())
                        )
                    }.also { it.setString(17, hashMap["objectId"] as String) }.executeUpdate()
            } catch (e: Exception) {
                println("[ERROR] 数据库插入失败")
                println("[ERROR] ${e.message}")
                throw e
            }
        }
        return true
    }
}