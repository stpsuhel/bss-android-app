package tk.suhel.bondusomobaysomity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ArrayAdapter extends RecyclerView.Adapter<ArrayAdapter.ViewHolder> {

    public List<UserModel> userModelList;
    public Context context;

    public ArrayAdapter(List<UserModel> userModelList, Context context) {
        this.userModelList = userModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item_adapter, parent, false);
        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserModel userModel = userModelList.get(position);
        holder.userFullName.setText(userModel.getName());
        holder.userEmail.setText(userModel.getEmail());
    }

    @Override
    public int getItemCount() {
        return userModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView userFullName, userEmail;

        public ViewHolder(@NonNull View itemView, Context context) {
            super(itemView);

            itemView.setOnClickListener(this);

            userFullName = itemView.findViewById(R.id.userFullName);
            userEmail = itemView.findViewById(R.id.userEmail);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            UserModel user = userModelList.get(position);

            Toast.makeText(context, user.getName(), Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(context, UserDetails.class);
            intent.putExtra("username", user.getUserName());
            context.startActivity(intent);
        }
    }
}
