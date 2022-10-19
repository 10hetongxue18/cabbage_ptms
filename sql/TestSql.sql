show databases;

use cab_ptms;

select a.userAccount,userPassword,b.name from tb_user a,tb_stu_info b where a.userAccount=b.stuId and a.userAccount=202004260048;

# select a.userAccount,userPassword,b.name from tb_user a,stu_info b where a.userAccount=b.stuId


select a.userName,userAccount,b.creator,planName,content from tb_user a, tb_plan b where a.id=b.userId and b.id=1;


# 查询校内实习指导老师表
select a.id,b.name,workId,school from tb_mentor a,tb_sch_tea b where a.teaRole=b.teaRole and a.teaRole=0;
# 查询企业实习指导老师表
select a.id,c.name,entLoginId,entName,department  from tb_mentor a,tb_ent_tea c where a.teaRole=c.teaRole and a.teaRole=1;

# 查询id为2的留言并且查询学生信息和账号昵称（评论）
select a.userName,userAccount,b.content,c.name from tb_user a, tb_guestbook b, tb_stu_info c where b.userId=2 and a.id=b.userId and a.userAccount=c.stuId ;


# 查询留言id为4的点赞情况
select a.content,b.userId,c.userName from tb_guestbook a,tb_like_num b,tb_user c where a.id=4 and a.id=b.guestId and a.userId=c.id;

select a.content,b.userId from tb_guestbook a,tb_like_num b where a.id=3 and a.id=b.guestId;

# 查询实习报告
select title,content from tb_report where userId=3;

# 修改实习报告id为3的报告状态未通过
update tb_report set repState=1 where id=3;

# 查询实习报告状态为待审核
select * from tb_report where repState=0;

# 修改实习报告id为1的报告状态通过
update tb_report set repState=2 where id=1;

# 查询符合打实习成绩的报告
select a.title,content,b.score,appraise from tb_report a,tb_result b where a.repState=2 and a.id=b.reportId


