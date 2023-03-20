package com.gyroh.a3rd_week.FriendsParts


import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.gyroh.a3rd_week.Fragment.friendsFragment
import com.gyroh.a3rd_week.databinding.ItemProfileBinding




class ProfileAdapter(var profileList : ArrayList<Profile>) : RecyclerView.Adapter<ProfileAdapter.CustomViewHolder>() {
    //class PofileAdapter로 생성자로들어갈게 val profileList라는게 ArrayList의 타입 Profile을 불러와서 리스트와 시킴
    // : -> 상속개념 상속을 받아오는데 RecyclerView.Adapter속성을 걸어오고 <ProfileAdapter.별도의 이름을 짓는 뷰홀더 클래스를 만듬>()

    interface OnItemClickListener{
        fun onItemClick(v:View, data: Profile, pos : Int)
    }
    private var listener : OnItemClickListener? = null
    fun setOnItemClickListener(listener : OnItemClickListener) {
        this.listener = listener
    }



    // onCreateViewHolder -> 액티비티가 xml에 연결하는하는 것처럼 listitem의 화면을 붙히는 작업
    //context -> 액티비티에서 담고있는 모든 정보
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val binding = ItemProfileBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return CustomViewHolder(binding).also { holder ->

            holder.binding.root.setOnClickListener {
                var asdf = holder.adapterPosition
                Log.d("디버깅","이름 : ${profileList[holder.adapterPosition].name},상테 메세지 : ${profileList[holder.adapterPosition].message}")
                Log.d("디버깅","$asdf")
            }
            //sharedpreferences랑 intent로 주는거 포기하기 아쉬운데 다음에 다시 한번더 도전!!
//            holder.binding.root.setOnLongClickListener {
//                val intent = Intent(holder.binding.root?.context, friendsFragment::class.java)
//                intent.putExtra("position",holder.adapterPosition)
//                ContextCompat.startActivity(holder.binding.root.context,intent,null)
//                return@setOnLongClickListener true
//            }
        }
    }

    //실질적으로 oncreateviewholder로 만들어진것을 연결해주는 역할
    //뷰에 대해서 안정적으로 모든 데이터를 매치시켜주는 곳
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind(profileList[position])
    }

    override fun getItemCount(): Int { //리스트에 대한 총 갯수
        return profileList.size
    }

    // viewHolder어탭터의 외부 혹은 내부에
    // CustomViewHolder -> 음료수 처럼 잡아주는 홀더처럼 view를 잡아주는 개념
    // 생성자를 만드는데 View타입을 가진 itemView를 만드는데
    //  : -> 상속  / 미리 만들어진 RecyclerView의 뷰홀더를 가져온다. 안에는 itemView같이 활용
    inner class CustomViewHolder(val binding : ItemProfileBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(profile: Profile) {
            binding.imageViewProfile.setImageResource(profile.picture)
            binding.textprofilename.text = profile.name
            binding.textprofilemessage.text = profile.message


            val pos = adapterPosition
            if(pos != RecyclerView.NO_POSITION)
            {
                itemView.setOnClickListener {
                    listener?.onItemClick(itemView,profile,pos)
                }
            }
        }

    }

    //여기 sharedpreferences되면 지우기
    var currentPosition = 0
    fun getPosition():Int{ return currentPosition; }
    fun setPosition(position: Int){ currentPosition = position }


    fun addItem(profile: Profile){
        profileList.add(profile)
        notifyDataSetChanged()
    }

    fun deleteItem(position: Int){
        profileList.removeAt(position)
        notifyItemRemoved(position)
        //notifyDataSetChanged()
    }

}